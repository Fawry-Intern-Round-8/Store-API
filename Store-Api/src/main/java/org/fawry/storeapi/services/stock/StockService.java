package org.fawry.storeapi.services.stock;

import org.fawry.storeapi.dtos.stock.StockConsumeRequestDTO;
import org.fawry.storeapi.dtos.stock.StockConsumeResponseDTO;
import org.fawry.storeapi.dtos.stock.StockRequestDTO;
import org.fawry.storeapi.dtos.stock.StockResponseDTO;
import org.fawry.storeapi.entities.Store;

import java.util.List;

public interface StockService {

    Long isProductAvailable(Long productId, int quantity);
    StockResponseDTO createStock(StockRequestDTO stockRequestDTO);
    StockResponseDTO addStock(StockRequestDTO stockRequestDTO);
    List<StockConsumeResponseDTO> consumeStock(StockConsumeRequestDTO stockConsumeRequestDTO);

    void deleteStockById(Long stockId);

    Store getStoreByStockId(Long stockId);


}
