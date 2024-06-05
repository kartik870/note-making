package com.kartik.noteapp.services.impl;

import com.kartik.noteapp.dtos.AuthDTO;
import com.kartik.noteapp.dtos.LoginDTO;
import com.kartik.noteapp.dtos.SignUpDTO;
import com.kartik.noteapp.entities.User;
import com.kartik.noteapp.repositories.UserRepository;
import com.kartik.noteapp.services.AuthenticationService;
import com.kartik.noteapp.services.JwtService;
import com.kartik.noteapp.utils.UserUtil;
import com.kartik.noteapp.exception.BadCredentialException;

import lombok.RequiredArgsConstructor;

import static com.kartik.noteapp.constants.ExceptionConstants.BAD_CREDENTIALS_FOUND;
import static com.kartik.noteapp.constants.ExceptionConstants.USERNAME_NOT_FOUND;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;


    @Override
    public AuthDTO signUp(SignUpDTO request) {
        userUtil.checkIfUserExist(request.getUserName());
        userUtil.checkIfStrongPassword(request.getPassword());

        User user = User.builder().userName(request.getUserName())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        //TODO: add refreshToken
        return AuthDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .accessToken(jwt)
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public String getAuthenticatedUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }

    @Override
    public AuthDTO signIn(LoginDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialException(BAD_CREDENTIALS_FOUND);
        }
        User user = userRepository.findByUserName(request.getUserName()).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, request.getUserName()))
        );

        var jwt = jwtService.generateToken(user);

        return AuthDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .accessToken(jwt)
                .createdAt(user.getCreatedAt())
                .build();
    }
}
