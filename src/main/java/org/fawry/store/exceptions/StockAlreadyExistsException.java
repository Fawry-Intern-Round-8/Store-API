package org.fawry.store.exceptions;

public class StockAlreadyExistsException extends RuntimeException {
    public StockAlreadyExistsException(String message) {
        super(message);
    }
}
