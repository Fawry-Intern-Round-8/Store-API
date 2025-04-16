package org.fawry.store.exceptions;

public class StockNotEnoughException extends RuntimeException{
    public StockNotEnoughException(String message) {
        super(message);
    }
}
