package com.kartik.noteapp.constants;

public class ExceptionConstants {
    public static final String USERNAME_NOT_FOUND = "User with given username %s not found.";
    public static final String GIVEN_USERNAME_NOT_FOUND = "Given username not found.";
    public static final String USERNAME_ALREADY_FOUND = "User with given username %s already exists.";
    public static final String WEAK_PASSWORD_FOUND = "Password must contains at-least 8 characters or must not be greater than 16 characters"
            + " or must contain special character or uppercase or lowercase letter.";
    public static final String BAD_CREDENTIALS_FOUND = "Invalid username or password.";
    public static final String NOTE_WITH_ID_NOT_FOUND = "Note with given Id not found";
    public static final String NOTE_WITH_THEMSELVES = "User cannot share a note with themselves.";

    private ExceptionConstants() {
    }
}
