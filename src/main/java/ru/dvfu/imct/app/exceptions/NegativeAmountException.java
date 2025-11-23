package ru.dvfu.imct.app.exceptions;

public class NegativeAmountException extends IllegalArgumentException{
    public NegativeAmountException(String message) {
        super(message);
    }
}
