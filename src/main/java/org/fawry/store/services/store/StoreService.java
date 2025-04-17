package org.fawry.store.services.store;

import org.fawry.store.dtos.stock.StockDTO;
import org.fawry.store.dtos.store.StoreDTO;
import org.fawry.store.dtos.store.StoreResponseDTO;
import java.util.List;

public interface StoreService {
    StoreResponseDTO createStore(String name, String address, double longitude, double latitude);

    StoreResponseDTO updateStore(Long id, String name, String address, double longitude, double latitude);

    List<StoreResponseDTO> getAllStores();

    StoreDTO findStoreById(Long id);
    void deleteStoreById(Long id);

    boolean storeExists(Long id);

    List<StockDTO> getAllStocksForStore(Long id, int page, int size);

    int getTotalStockCount(Long storeId);
}
