package org.fawry.storeapi.exceptions;

public class ProductDoesNotExist extends RuntimeException {
    public ProductDoesNotExist(String message) {
        super(message);
    }
}
