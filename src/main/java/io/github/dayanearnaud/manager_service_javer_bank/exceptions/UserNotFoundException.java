package io.github.dayanearnaud.manager_service_javer_bank.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User does not exist.");
    }
}
