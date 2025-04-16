package org.fawry.storeapi.mappers;

import org.fawry.storeapi.dtos.store.StoreDTO;
import org.fawry.storeapi.entities.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public static StoreDTO toDTO(Store store){
        return new StoreDTO(
                store.getId(),
                store.getName(),
                store.getAddress()
        );
    }

}
