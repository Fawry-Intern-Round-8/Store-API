package org.fawry.storeapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fawry.storeapi.entities.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreWithDistanceDTO {
    private Long id;
    private String name;
    private String address;
    private double distance;

}

