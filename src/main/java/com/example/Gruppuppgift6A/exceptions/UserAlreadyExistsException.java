package com.example.Gruppuppgift6A.exceptions;

public class UserAlreadyExistsException extends CreateUserException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
