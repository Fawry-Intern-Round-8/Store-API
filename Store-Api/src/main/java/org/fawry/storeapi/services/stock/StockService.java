package org.fawry.storeapi.services.stock;

public interface StockService {

    public boolean isProductAvailable(Long productId, int quantity);
}
