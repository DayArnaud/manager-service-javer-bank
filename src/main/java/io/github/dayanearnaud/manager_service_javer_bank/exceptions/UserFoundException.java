package io.github.dayanearnaud.manager_service_javer_bank.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("User already exists.");
    }
}
