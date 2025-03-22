package org.fawry.storeapi.services.stockhistory;

import org.fawry.storeapi.entities.StockTransactionsHistory;
import org.fawry.storeapi.entities.Store;
import org.fawry.storeapi.entities.TransactionType;
import org.fawry.storeapi.repositories.StockTransactionsHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class StockTransactionsHistoryServiceImpl implements StockTransactionsHistoryService {
    StockTransactionsHistoryRepository stockTransactionsHistoryRepository;

    public StockTransactionsHistoryServiceImpl(StockTransactionsHistoryRepository stockTransactionsHistoryRepository) {
        this.stockTransactionsHistoryRepository = stockTransactionsHistoryRepository;
    }

    @Override
    public void logTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType) {
        StockTransactionsHistory stockTransactionsHistory = new StockTransactionsHistory();
        stockTransactionsHistory.setStore(store);
        stockTransactionsHistory.setProductId(productId);
        stockTransactionsHistory.setOldQuantity(oldQuantity);
        stockTransactionsHistory.setNewQuantity(newQuantity);
        stockTransactionsHistory.setTransactionType(transactionType);

        stockTransactionsHistoryRepository.save(stockTransactionsHistory);
    }
}
