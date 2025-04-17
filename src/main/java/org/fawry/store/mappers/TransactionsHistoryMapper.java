package org.fawry.store.mappers;

import org.fawry.store.dtos.transactionshistory.TransactionsHistoryDTO;
import org.fawry.store.entities.StockTransactionsHistory;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionsHistoryMapper {

    public static TransactionsHistoryDTO toDto(StockTransactionsHistory entity) {
        return TransactionsHistoryDTO.builder()
                .transactionId(entity.getId())
                .storeId(entity.getStore().getId())
                .productId(entity.getProductId())
                .oldQuantity(entity.getOldQuantity())
                .newQuantity(entity.getNewQuantity())
                .transactionType(entity.getTransactionType())
                .consumerEmail(entity.getConsumerEmail())
                .createdAt(entity.getCreatedAt() != null ?
                        Date.from(entity.getCreatedAt()) : null)
                .build();
    }

    public static List<TransactionsHistoryDTO> toDtoList(List<StockTransactionsHistory> entities) {
        return entities.stream()
                .map(TransactionsHistoryMapper::toDto)
                .collect(Collectors.toList());
    }
}