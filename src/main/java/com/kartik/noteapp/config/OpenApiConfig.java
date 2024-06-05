package com.kartik.noteapp.config;

import static com.kartik.noteapp.constants.OpenApiConstants.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = SWAGGER_TITLE,
                description = NOTE_APP_BACKEND_SPECIFICATION,
                version = API_VERSION
        ))
@SecurityScheme(
        name = BEARER_AUTH,
        type = SecuritySchemeType.HTTP,
        bearerFormat = JWT,
        scheme = BEARER,
        in = SecuritySchemeIn.HEADER,
        description = JWT_AUTH
)
public class OpenApiConfig {

}
