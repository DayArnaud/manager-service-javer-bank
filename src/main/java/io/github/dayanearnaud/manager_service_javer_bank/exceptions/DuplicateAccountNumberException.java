package io.github.dayanearnaud.manager_service_javer_bank.exceptions;

public class DuplicateAccountNumberException extends RuntimeException {
    public DuplicateAccountNumberException(String message) {
        super(message);
    }
}
