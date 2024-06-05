package com.kartik.noteapp.services;

import org.springframework.security.core.Authentication;

import com.kartik.noteapp.dtos.AuthDTO;
import com.kartik.noteapp.dtos.LoginDTO;
import com.kartik.noteapp.dtos.SignUpDTO;

public interface AuthenticationService {

    AuthDTO signUp(SignUpDTO request);

    AuthDTO signIn(LoginDTO request);

    String getAuthenticatedUser(Authentication authentication);
}
