package org.fawry.store.dtos.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private Long id;
    private Long storeId;
    private Long productId;
    private int quantity;
    private double price;
}
