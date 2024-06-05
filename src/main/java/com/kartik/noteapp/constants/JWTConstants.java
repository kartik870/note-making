package com.kartik.noteapp.constants;

/**
 * Constants class for JWT (JSON Web Token) expiration times.
 */
public class JWTConstants {
    /**
     * The expiration time for access tokens in milliseconds.
     * Access tokens are used for short-term authorization.
     * Default value: 60 minutes.
     */
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 3600000;
    /**
     * The expiration time for refresh tokens in milliseconds.
     * Refresh tokens are used to obtain new access tokens without requiring the user to re-enter their credentials.
     * Default value: 30 days.
     */
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30;

    private JWTConstants() {
    }
}
