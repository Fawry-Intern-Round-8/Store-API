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
}
