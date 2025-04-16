package org.fawry.store.services.stockhistory;

import org.fawry.store.entities.Store;
import org.fawry.store.entities.TransactionType;

public interface StockTransactionsHistoryService {
    void logInternalTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType);

    void logConsumerTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType, String consumerEmail);
}
