package org.fawry.storeapi.mappers;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(source = "store.id", target = "storeId") // Map Store ID to storeId
    StockDTO toDTO(Stock stock);

    @Mapping(source = "storeId", target = "store.id") // Map storeId back to Store (Only if necessary)
    Stock toEntity(StockDTO stockDTO);
}
