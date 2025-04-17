package org.fawry.storeapi.services.stock;


import org.fawry.storeapi.dtos.stock.StockConsumeRequestDTO;
import org.fawry.storeapi.dtos.stock.StockConsumeResponseDTO;
import org.fawry.storeapi.dtos.stock.StockRequestDTO;
import org.fawry.storeapi.dtos.stock.StockResponseDTO;
import org.fawry.storeapi.entities.Stock;
import org.fawry.storeapi.entities.Store;
import org.fawry.storeapi.entities.TransactionType;
import org.fawry.storeapi.exceptions.ProductDoesNotExist;
import org.fawry.storeapi.exceptions.StockAlreadyExistsException;
import org.fawry.storeapi.exceptions.StockNotFountException;
import org.fawry.storeapi.repositories.StockRepository;
import org.fawry.storeapi.repositories.StoreRepository;
import org.fawry.storeapi.services.client.ProductClientService;
import org.fawry.storeapi.services.stockhistory.StockTransactionsHistoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;
    private final StoreRepository storeRepository;
    private final StockTransactionsHistoryService stockTransactionsHistoryService;
    private final ProductClientService productClientService;


    public StockServiceImpl(StockRepository stockRepository, StoreRepository storeRepository, StockTransactionsHistoryService stockTransactionsHistoryService, ProductClientService productClientService) {
        this.stockRepository = stockRepository;
        this.storeRepository = storeRepository;
        this.stockTransactionsHistoryService = stockTransactionsHistoryService;
        this.productClientService = productClientService;
    }

    public Long isProductAvailable(Long productId, int quantity) {

        if( stockRepository.getTotalProductQuantity(productId) >= quantity) {
            return 1L;
        } else return 0L;
    }

    @Override
    public StockResponseDTO createStock(StockRequestDTO stockRequestDTO) {
        Long storeId = stockRequestDTO.getStoreId();
        Long productId = stockRequestDTO.getProductId();
        int quantity = stockRequestDTO.getQuantity();

        boolean productExists = productClientService.checkProductExists(productId);
        if (!productExists) {
            throw new ProductDoesNotExist("Product: " + productId + " not found");
        }

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

    @Transactional
    @Override
    public List<StockConsumeResponseDTO> consumeStock(StockConsumeRequestDTO stockConsumeRequestDTO) {

        Long productId = stockConsumeRequestDTO.getProductId();

        boolean productExists = productClientService.checkProductExists(productId);
        if (!productExists) {
            throw new ProductDoesNotExist("Product: " + productId + " not found");
        }

        int quantity = stockConsumeRequestDTO.getQuantity();
        String customerEmail =stockConsumeRequestDTO.getCustomerEmail();
        double longitude = stockConsumeRequestDTO.getLongitude();
        double latitude = stockConsumeRequestDTO.getLatitude();
        Long totalProductQuantity = stockRepository.getTotalProductQuantity(productId);
        boolean isAvailableQuantity = totalProductQuantity > quantity;
        if (!isAvailableQuantity) {
            throw new StockNotFountException("Not enough stock available");
        }

        int remaining = quantity;
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Object[]> stores = storeRepository.findNearestStoresWithProduct(productId, longitude, latitude, pageable).getContent();

        List<StockConsumeResponseDTO> consumptionReport = new ArrayList<>();

        for (Object[] row : stores) {
            Long storeId = ((Number) row[0]).longValue();
            String storeName = (String) row[1];
            String storeAddress = (String) row[2];
            double distance = ((Number) row[3]).doubleValue();
            int availableQuantity = ((Number) row[4]).intValue();

            int consumed = Math.min(remaining, availableQuantity);

            stockRepository.decreaseQuantity(storeId, productId, consumed);

            Store store = storeRepository.findById(storeId).orElseThrow();

            stockTransactionsHistoryService.logConsumerTransaction(
                    store, productId,
                    availableQuantity,
                    availableQuantity - consumed,
                    TransactionType.CONSUME,
                    customerEmail
            );

            consumptionReport.add(new StockConsumeResponseDTO(storeId, storeName, storeAddress, distance, consumed));

            remaining -= consumed;
            if (remaining == 0) break;
        }

        // TODO: Should pass to notifications api

        return consumptionReport;
    }

    @Override
    public Long getTotalProductQuantity( Long productId){
        return stockRepository.getTotalProductQuantity(productId);
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
    public Store getStoreByStockId(Long stockId) {
        return stockRepository.findStoreByStockId(stockId);
    }

}
