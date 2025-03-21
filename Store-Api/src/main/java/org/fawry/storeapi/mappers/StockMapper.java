package org.fawry.storeapi.mappers;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDTO toDTO(Stock stock);
    Stock toEntity(StockDTO stockDTO);
}
