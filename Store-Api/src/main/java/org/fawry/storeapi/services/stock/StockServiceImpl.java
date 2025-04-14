package org.fawry.storeapi.services.stock;


import org.fawry.storeapi.dtos.stock.StockRequestDTO;
import org.fawry.storeapi.dtos.stock.StockResponseDTO;
import org.fawry.storeapi.entities.Stock;
import org.fawry.storeapi.entities.Store;
import org.fawry.storeapi.entities.TransactionType;
import org.fawry.storeapi.exceptions.StockAlreadyExistsException;
import org.fawry.storeapi.exceptions.StockNotFountException;
import org.fawry.storeapi.repositories.StockRepository;
import org.fawry.storeapi.repositories.StoreRepository;
import org.fawry.storeapi.services.stockhistory.StockTransactionsHistoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;
    private final StoreRepository storeRepository;
    private final StockTransactionsHistoryService stockTransactionsHistoryService;

    public StockServiceImpl(StockRepository stockRepository, StoreRepository storeRepository, StockTransactionsHistoryService stockTransactionsHistoryService) {
        this.stockRepository = stockRepository;
        this.storeRepository = storeRepository;
        this.stockTransactionsHistoryService = stockTransactionsHistoryService;
    }

    public Long isProductAvailable(Long productId, int quantity) {
        System.out.println(stockRepository.isProductAvailableInAnyStore(productId, quantity));
        return stockRepository.isProductAvailableInAnyStore(productId, quantity);
    }

    @Override
    public StockResponseDTO createStock(StockRequestDTO stockRequestDTO) {
        Long storeId = stockRequestDTO.getStoreId();
        Long productId = stockRequestDTO.getProductId();
        int quantity = stockRequestDTO.getQuantity();

        // TODO: Check if the product exists using ProductAPI

        // Check if the product already exists in this store
        Optional<Stock> existingStockOpt = stockRepository.findStockByProductIdAndStoreId(productId, storeId);
        Store store = storeRepository.findStoreById(storeId);

        if (existingStockOpt.isPresent()) {
            Stock existingStock = existingStockOpt.get();

            if (existingStock.isAvailable()) {
                throw new StockAlreadyExistsException("Product ID " + productId + " is already assigned to another stock in this store.");
            } else {
                existingStock.setQuantity(quantity);
                existingStock.setAvailable(true);
                Stock updatedStock = stockRepository.save(existingStock);
                stockTransactionsHistoryService.logInternalTransaction(store, productId, 0, quantity, TransactionType.ADD);
                return mapToStockResponseDTO(updatedStock);
            }
        }


        Stock newStock = new Stock();
        newStock.setStore(store);
        newStock.setProductId(productId);
        newStock.setQuantity(quantity);
        Stock savedStock = stockRepository.save(newStock);
        stockTransactionsHistoryService.logInternalTransaction(store, productId, 0, quantity, TransactionType.ADD);

        return mapToStockResponseDTO(savedStock);
    }


    @Override
    public StockResponseDTO addStock(StockRequestDTO stockRequestDTO) {
        Long storeId = stockRequestDTO.getStoreId();
        Long productId = stockRequestDTO.getProductId();
        int addedQuantity = stockRequestDTO.getQuantity();

        Store store = storeRepository.findStoreById(storeId);

        // Find existing stock and update quantity or create a new stock
        Stock stock = stockRepository.findStockByProductIdAndStoreId(productId, storeId)
                .orElseGet(() -> {
                    Stock newStock = new Stock();
                    newStock.setStore(store);
                    newStock.setProductId(productId);
                    newStock.setQuantity(0);
                    return newStock;
                });

        int oldQuantity = stock.getQuantity();
        stock.setQuantity(oldQuantity + addedQuantity);
        stock.setAvailable(true);
        Stock savedStock = stockRepository.save(stock);
        stockTransactionsHistoryService.logInternalTransaction(store, productId, oldQuantity, savedStock.getQuantity(), TransactionType.ADD);

        return mapToStockResponseDTO(savedStock);
    }


    private StockResponseDTO mapToStockResponseDTO(Stock stock) {
        return StockResponseDTO.builder()
                .id(stock.getId())
                .storeId(stock.getStore().getId())
                .quantity(stock.getQuantity())
                .productId(stock.getProductId())
                .build();
    }

    @Override
    public void deleteStockById(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFountException("Stock not found with ID: " + stockId));
        Store store = getStoreByStockId(stockId);

        stockTransactionsHistoryService.logInternalTransaction(store, stock.getProductId(), stock.getQuantity(),0, TransactionType.CONSUME);
        // Deleting the stock
        stock.setQuantity(0);
        stock.setAvailable(false);
        stockRepository.save(stock);

    }


    @Override
    public boolean stockExists(Long id) {
        return stockRepository.findById(id).isPresent();
    }

    @Override
    public Store getStoreByStockId(Long stockId) {
        return stockRepository.findStoreByStockId(stockId);
    }

}
