package org.fawry.store.dtos.stock;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDTO {
    private Long id;
    private Long storeId;
    private Long productId;
    private int quantity;
}
