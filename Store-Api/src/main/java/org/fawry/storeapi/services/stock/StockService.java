package org.fawry.storeapi.services.stock;

import org.fawry.storeapi.dtos.stock.StockRequestDTO;
import org.fawry.storeapi.dtos.stock.StockResponseDTO;
import org.fawry.storeapi.entities.Store;

public interface StockService {

    Long isProductAvailable(Long productId, int quantity);

    void deleteStockById(Long stockId);
    boolean stockExists(Long id);

    Store getStoreByStockId(Long stockId);


}
