package org.fawry.store.services.stockhistory;

import org.fawry.store.dtos.transactionshistory.TransactionsHistoryDTO;
import org.fawry.store.entities.Store;
import org.fawry.store.entities.TransactionType;

import java.util.List;

public interface StockTransactionsHistoryService {
    void logInternalTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType);
    void logConsumerTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType, String consumerEmail);
    List<TransactionsHistoryDTO> getAllTransactions();
}
