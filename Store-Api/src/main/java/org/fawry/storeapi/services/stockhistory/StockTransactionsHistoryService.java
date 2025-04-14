package org.fawry.storeapi.services.stockhistory;

import org.fawry.storeapi.entities.Store;
import org.fawry.storeapi.entities.TransactionType;

public interface StockTransactionsHistoryService {
    void logInternalTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType);
    void logConsumerTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType, String consumerEmail);
}
