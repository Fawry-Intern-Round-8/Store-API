package org.fawry.storeapi.services.store;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.dtos.StoreResponseDTO;

import java.util.List;

public interface StoreService {
    StoreResponseDTO createStore(String name, String address, double longitude, double latitude);

    StoreResponseDTO updateStore(Long id, String name, String address, double longitude, double latitude);

    List<StoreResponseDTO> getAllStores();

    StoreDTO findStoreById(Long id);

    StoreDTO findStoreByName(String name);

    List<StoreResponseDTO> findNearestStores(double longitude, double latitude, double radius, int page, int size);

    void deleteStoreById(Long id);

    boolean storeExists(Long id);

    public List<StockDTO> getAllStocksForStore(Long id, int page, int size);

    int getTotalStockCount(Long storeId);
}
