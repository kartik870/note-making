package com.kartik.noteapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static com.kartik.noteapp.constants.OpenApiConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kartik.noteapp.constants.OpenApiConstants;
import com.kartik.noteapp.dtos.AuthDTO;
import com.kartik.noteapp.dtos.ErrorDTO;
import com.kartik.noteapp.dtos.LoginDTO;
import com.kartik.noteapp.dtos.SignUpDTO;
import com.kartik.noteapp.services.AuthenticationService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = AUTH_APIS, description = APIS_FOR_USER_AUTHENTICATION)
public class AuthController {
    private final AuthenticationService authenticationService;
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Operation(
            summary = OpenApiConstants.USER_REGISTRATION,
            description = OpenApiConstants.REGISTERS_A_NEW_USER,
            responses = {
                    @ApiResponse(
                            description = USER_SUCCESSFULLY_REGISTERED,
                            responseCode = CREATED,
                            content = @Content(mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = AuthDTO.class))
                    ),
                    @ApiResponse(
                            description = CONFLICT_USER_WITH_THE_SAME_EMAIL_ALREADY_EXISTS,
                            responseCode = CONFLICT,
                            content = @Content(mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            description = BAD_REQUEST_INVALID_INPUT,
                            responseCode = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<AuthDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        log.info("Received sign-up request for email: {}", signUpDTO.getUserName());
        AuthDTO dto = authenticationService.signUp(signUpDTO);
        log.info("User successfully registered with email: {}", signUpDTO.getUserName());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(

            summary = USER_LOGIN,
            description = LOGS_IN_AN_EXISTING_USER,
            responses = {
                    @ApiResponse(
                            description = USER_SUCCESSFULLY_LOGGED_IN,
                            responseCode = OK,
                            content = @Content(mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = AuthDTO.class))
                    ),
                    @ApiResponse(
                            description = UNAUTHORIZED_INVALID_CREDENTIALS,
                            responseCode = UNAUTHORIZED,
                            content = @Content(mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            description = BAD_REQUEST_INVALID_INPUT,
                            responseCode = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO loginDTO) {
        log.info("Received login request for email: {}", loginDTO.getUserName());
        AuthDTO authDTO = authenticationService.signIn(loginDTO);
        log.info("User successfully logged in with email: {}", loginDTO.getUserName());
        return new ResponseEntity<>(authDTO, HttpStatus.OK);
    }

}
