package org.fawry.storeapi.services.stock;

import org.fawry.storeapi.dtos.stock.StockConsumeRequestDTO;
import org.fawry.storeapi.dtos.stock.StockConsumeResponseDTO;
import org.fawry.storeapi.dtos.stock.StockRequestDTO;
import org.fawry.storeapi.dtos.stock.StockResponseDTO;
import org.fawry.storeapi.entities.Store;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockService {

    Long isProductAvailable(Long productId, int quantity);
    Long getTotalProductQuantity(@Param("productId") Long productId);


    StockResponseDTO createStock(StockRequestDTO stockRequestDTO);
    StockResponseDTO addStock(StockRequestDTO stockRequestDTO);
    List<StockConsumeResponseDTO> consumeStock(StockConsumeRequestDTO stockConsumeRequestDTO);

    void deleteStockById(Long stockId);

    Store getStoreByStockId(Long stockId);


    List<StockResponseDTO> getAllStocks(int page, int size);

    StockResponseDTO getStockById(Long stockId);
}
