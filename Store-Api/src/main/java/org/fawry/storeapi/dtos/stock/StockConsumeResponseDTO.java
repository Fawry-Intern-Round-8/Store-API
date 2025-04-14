package org.fawry.storeapi.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockConsumeResponseDTO {
    private Long id;
    private String name;
    private String address;
    private double distance;
    private int quantity;
}

