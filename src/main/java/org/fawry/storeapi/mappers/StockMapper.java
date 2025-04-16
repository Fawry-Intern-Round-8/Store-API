package org.fawry.storeapi.mappers;

import org.fawry.storeapi.dtos.stock.StockDTO;
import org.fawry.storeapi.entities.Stock;
public class StockMapper {
    public static StockDTO toDTO(Stock stock) {
        return new StockDTO(
                stock.getId(),
                stock.getStore().getId(),
                stock.getProductId(),
                stock.getQuantity(),
                stock.getProductId()
        );
    }
}
