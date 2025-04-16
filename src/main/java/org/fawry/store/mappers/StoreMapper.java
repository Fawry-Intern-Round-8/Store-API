package org.fawry.store.mappers;

import org.fawry.store.dtos.store.StoreDTO;
import org.fawry.store.entities.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public static StoreDTO toDTO(Store store) {
        return new StoreDTO(
                store.getId(),
                store.getName(),
                store.getAddress()
        );
    }

}
