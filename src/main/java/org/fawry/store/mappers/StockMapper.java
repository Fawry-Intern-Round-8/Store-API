package org.fawry.store.mappers;

import org.fawry.store.dtos.stock.StockDTO;
import org.fawry.store.entities.Stock;
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
