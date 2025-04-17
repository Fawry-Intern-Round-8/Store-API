package org.fawry.store.services.stockhistory;

import org.fawry.store.dtos.store.StoreResponseDTO;
import org.fawry.store.dtos.transactionshistory.TransactionsHistoryDTO;
import org.fawry.store.entities.StockTransactionsHistory;
import org.fawry.store.entities.Store;
import org.fawry.store.entities.TransactionType;
import org.fawry.store.mappers.TransactionsHistoryMapper;
import org.fawry.store.repositories.StockTransactionsHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StockTransactionsHistoryServiceImpl implements StockTransactionsHistoryService {
    StockTransactionsHistoryRepository stockTransactionsHistoryRepository;

    public StockTransactionsHistoryServiceImpl(StockTransactionsHistoryRepository stockTransactionsHistoryRepository) {
        this.stockTransactionsHistoryRepository = stockTransactionsHistoryRepository;
    }
    @Override
    public void logInternalTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType) {
        StockTransactionsHistory stockTransactionsHistory = new StockTransactionsHistory();
        stockTransactionsHistory.setStore(store);
        stockTransactionsHistory.setProductId(productId);
        stockTransactionsHistory.setOldQuantity(oldQuantity);
        stockTransactionsHistory.setNewQuantity(newQuantity);
        stockTransactionsHistory.setTransactionType(transactionType);
        stockTransactionsHistoryRepository.save(stockTransactionsHistory);
    }

    @Override
    public void logConsumerTransaction(Store store, Long productId, int oldQuantity, int newQuantity, TransactionType transactionType, String consumerEmail) {
        StockTransactionsHistory stockTransactionsHistory = new StockTransactionsHistory();
        stockTransactionsHistory.setStore(store);
        stockTransactionsHistory.setProductId(productId);
        stockTransactionsHistory.setOldQuantity(oldQuantity);
        stockTransactionsHistory.setNewQuantity(newQuantity);
        stockTransactionsHistory.setTransactionType(transactionType);
        stockTransactionsHistory.setConsumerEmail(consumerEmail);
        stockTransactionsHistoryRepository.save(stockTransactionsHistory);
    }

    @Override
    public List<TransactionsHistoryDTO> getAllTransactions() {
        List<StockTransactionsHistory> transactions = stockTransactionsHistoryRepository.findAll();
        return TransactionsHistoryMapper.toDtoList(transactions);
    }
}
