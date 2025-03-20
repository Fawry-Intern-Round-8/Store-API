package org.fawry.storeapi.mappers;

import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.entities.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    StoreDTO toDTO(Store store);
    Store toEntity(StoreDTO storeDTO);
}
