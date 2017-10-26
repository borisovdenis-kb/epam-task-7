package ru.intodayer.serializator.validator;


public class IncorrectJsonException extends RuntimeException {
    public IncorrectJsonException(String message) {
        super(message);
    }
}
