package org.fawry.storeapi.exceptions;

public class StockNotFountException extends RuntimeException{
    public StockNotFountException(String message) {
        super(message);
    }
}
