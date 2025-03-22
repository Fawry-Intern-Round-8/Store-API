package org.fawry.storeapi.services.stockhistory;

import org.fawry.storeapi.entities.Stock;
import org.fawry.storeapi.entities.StockTransactionsHistory;
import org.fawry.storeapi.entities.TransactionType;
import org.fawry.storeapi.repositories.StockRepository;
import org.fawry.storeapi.repositories.StockTransactionsHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class StockTransactionsHistoryServiceImpl implements StockTransactionsHistoryService {
    StockTransactionsHistoryRepository stockTransactionsHistoryRepository;
    StockRepository stockRepository;

    @Override
    public void logTransaction(Long stockId, int oldQuantity, int newQuantity, TransactionType transactionType) {
        StockTransactionsHistory stockTransactionsHistory = new StockTransactionsHistory();
        Stock stock = stockRepository.findById(stockId).get();
        stockTransactionsHistory.setStock(stock);
        stockTransactionsHistory.setOldQuantity(oldQuantity);
        stockTransactionsHistory.setNewQuantity(newQuantity);
        stockTransactionsHistory.setTransactionType(transactionType);
        stockTransactionsHistoryRepository.save(stockTransactionsHistory);
    }
}
