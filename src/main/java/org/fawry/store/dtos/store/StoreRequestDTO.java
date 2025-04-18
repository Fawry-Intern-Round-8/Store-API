package org.fawry.store.dtos.store;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StoreRequestDTO {
    private String name;
    private String address;
    private double longitude;
    private double latitude;
}
