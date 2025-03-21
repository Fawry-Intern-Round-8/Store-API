package org.fawry.storeapi.mappers;

import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.entities.Store;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreDTO toDTO(Store store);
    Store toEntity(StoreDTO storeDTO);
}
