package org.fawry.storeapi.dtos.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRequestDTO {
    private Long storeId;
    private Long productId;
    private int quantity;
}
