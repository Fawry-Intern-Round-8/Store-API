package org.fawry.storeapi.services.store;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.dtos.StoreResponseDTO;
import org.fawry.storeapi.dtos.StoreWithDistanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    StoreResponseDTO createStore(String name, String address, double longitude, double latitude);

    StoreResponseDTO updateStore(Long id, String name, String address, double longitude, double latitude);

    List<StoreResponseDTO> getAllStores();

    StoreDTO findStoreById(Long id);

    StoreDTO findStoreByName(String name);

    Page<StoreWithDistanceDTO> findNearestStores(double longitude, double latitude, double radius, Pageable pageable);
    Page<StoreWithDistanceDTO> findNearestStoresWithProduct(Long productId, double longitude, double latitude, double radius, Pageable pageable);
    void deleteStoreById(Long id);

    boolean storeExists(Long id);

    List<StockDTO> getAllStocksForStore(Long id, int page, int size);

    int getTotalStockCount(Long storeId);
}
