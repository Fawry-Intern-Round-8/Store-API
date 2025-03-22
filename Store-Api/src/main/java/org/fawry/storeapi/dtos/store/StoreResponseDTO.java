package org.fawry.storeapi.dtos.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StoreResponseDTO {
    private Long id;
    private String name;

    private String address;

    private double longitude;
    private double latitude;
}
