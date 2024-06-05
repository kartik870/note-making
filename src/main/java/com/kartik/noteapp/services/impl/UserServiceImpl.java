package com.kartik.noteapp.services.impl;

import lombok.RequiredArgsConstructor;

import static com.kartik.noteapp.constants.ExceptionConstants.USERNAME_NOT_FOUND;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kartik.noteapp.repositories.UserRepository;
import com.kartik.noteapp.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username))
        );
    }
}
