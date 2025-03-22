package org.fawry.storeapi.services.stockhistory;

import org.fawry.storeapi.entities.TransactionType;

public interface StockTransactionsHistoryService {
    void logTransaction(Long stockId, int oldQuantity, int newQuantity, TransactionType transactionType);
}
