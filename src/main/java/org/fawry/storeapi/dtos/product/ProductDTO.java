package org.fawry.storeapi.dtos.product;

import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int quantity;

}