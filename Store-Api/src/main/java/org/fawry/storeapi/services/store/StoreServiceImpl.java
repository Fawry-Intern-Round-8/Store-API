package org.fawry.storeapi.services.store;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.dtos.StoreResponseDTO;
import org.fawry.storeapi.entities.Stock;
import org.fawry.storeapi.entities.Store;
import org.fawry.storeapi.mappers.StockMapper;
import org.fawry.storeapi.mappers.StoreMapper;
import org.fawry.storeapi.repositories.StockRepository;
import org.fawry.storeapi.repositories.StoreRepository;
import org.fawry.storeapi.services.location.LocationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    private final StoreMapper storeMapper;

    private final LocationServiceImpl locationService;


    public StoreServiceImpl(StoreRepository storeRepository, StockRepository stockRepository,
                            StockMapper stockMapper, StoreMapper storeMapper,
                            LocationServiceImpl locationService) {
        this.storeRepository = storeRepository;
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.storeMapper = storeMapper;
        this.locationService=locationService;
    }

    @Override
    public StoreResponseDTO createStore(String name, String address, double longitude, double latitude) {
        Store store = new Store();
        store.setName(name);
        store.setAddress(address);
        store.setLocation(locationService.createPoint(longitude,latitude));

        Store savedStore = storeRepository.save(store);
        new StoreResponseDTO();
        return StoreResponseDTO.builder()
                .id(savedStore.getId())
                .address(savedStore.getAddress())
                .name(savedStore.getName())
                .longitude(savedStore.getLocation().getX())
                .latitude(savedStore.getLocation().getY())
                .build();
    }

    @Override
    public StoreResponseDTO updateStore(Long id, String name, String address, double longitude, double latitude) {
        Store store = storeRepository.findStoreById(id);
        // Should return an error if didn't find
        store.setName(name);
        store.setAddress(address);
        store.setLocation(locationService.createPoint(longitude,latitude));
        Store updatedStore = storeRepository.save(store);
        return StoreResponseDTO.builder()
                .id(updatedStore.getId())
                .address(updatedStore.getAddress())
                .name(updatedStore.getName())
                .longitude(updatedStore.getLocation().getX())
                .latitude(updatedStore.getLocation().getY())
                .build();

    }

    @Override
    public StoreDTO findStoreById(Long id) {
        // Returning an error here too
        Store store = storeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Store not found"));
        return storeMapper.toDTO(store);
    }

    @Override
    public StoreDTO findStoreByName(String name) {
        return storeMapper.toDTO(storeRepository.findStoreByName(name));
    }

    @Override
    public List<StoreResponseDTO> findNearestStores(double longitude, double latitude, double radius, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StoreResponseDTO> storePage = storeRepository.findNearestStores(longitude, latitude, radius, pageable);
        return storePage.getContent();
    }

    @Override
    public void deleteStoreById(Long id) {
        if (storeExists(id)) {
            storeRepository.deleteById(id);
        } else {
            // Return an error
            System.out.println("Store Doesn't Exist");
        }
    }

    @Override
    public boolean storeExists(Long id) {
        return storeRepository.findById(id).isPresent();
    }

    @Override
    public List<StockDTO> getAllStocksForStore(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Stock> stockPage = stockRepository.findByStoreId(id, pageable);

        return stockPage.map(stockMapper::toDTO).getContent();
    }

    @Override
    public int getTotalStockCount(Long storeId) {
        return stockRepository.getTotalStockCountByStoreId(storeId);
    }
}
