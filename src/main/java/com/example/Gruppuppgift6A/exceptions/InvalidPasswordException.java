package com.example.Gruppuppgift6A.exceptions;

public class InvalidPasswordException extends CreateUserException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
