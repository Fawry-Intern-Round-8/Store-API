package org.fawry.storeapi.services.stockhistory;

import org.fawry.storeapi.entities.Store;
import org.fawry.storeapi.entities.TransactionType;

public interface StockTransactionsHistoryService {
    void logTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType);
}
