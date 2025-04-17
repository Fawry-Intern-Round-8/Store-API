package org.fawry.store.services.stock;


import org.fawry.store.dtos.stock.StockConsumeRequestDTO;
import org.fawry.store.dtos.stock.StockConsumeResponseDTO;
import org.fawry.store.dtos.stock.StockRequestDTO;
import org.fawry.store.dtos.stock.StockResponseDTO;
import org.fawry.store.entities.Store;
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


}
