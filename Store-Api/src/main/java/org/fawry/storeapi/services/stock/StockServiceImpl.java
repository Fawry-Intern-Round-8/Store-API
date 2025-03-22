package org.fawry.storeapi.services.stock;


import org.fawry.storeapi.repositories.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public boolean isProductAvailable(Long productId, int quantity) {
        return stockRepository.isProductAvailableInAnyStore(productId, quantity);
    }



    @Override
    public void deleteStockById(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFountException("Stock not found with ID: " + stockId));
        Store store = getStoreByStockId(stockId);

        stockTransactionsHistoryService.logTransaction(store, stock.getProductId(), stock.getQuantity(),0, TransactionType.CONSUME);
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
