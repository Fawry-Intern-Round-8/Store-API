package org.fawry.storeapi.exceptions;

public class StockAlreadyExistsException extends RuntimeException {
    public StockAlreadyExistsException(String message) {
        super(message);
    }
}
