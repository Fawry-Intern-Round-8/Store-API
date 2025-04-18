package org.fawry.store.services.store;

import org.fawry.store.dtos.stock.StockDTO;
import org.fawry.store.dtos.store.StoreDTO;
import org.fawry.store.dtos.store.StoreResponseDTO;
import org.fawry.store.entities.Stock;
import org.fawry.store.entities.Store;
import org.fawry.store.mappers.StockMapper;
import org.fawry.store.mappers.StoreMapper;
import org.fawry.store.repositories.StockRepository;
import org.fawry.store.repositories.StoreRepository;
import org.fawry.store.services.location.LocationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;




    private final LocationServiceImpl locationService;


    public StoreServiceImpl(StoreRepository storeRepository, StockRepository stockRepository,
                            LocationServiceImpl locationService) {
        this.storeRepository = storeRepository;
        this.stockRepository = stockRepository;
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
    public List<StoreResponseDTO> getAllStores() {
        Iterable<Store> stores = storeRepository.findAll();
        // Convert Iterable to Stream
        return StreamSupport.stream(stores.spliterator(), false)
                .map(store -> StoreResponseDTO.builder()
                        .id(store.getId())
                        .name(store.getName())
                        .address(store.getAddress())
                        .longitude(store.getLocation().getX())
                        .latitude(store.getLocation().getY())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public StoreDTO findStoreById(Long id) {
        // Returning an error here too
        Store store = storeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Store not found"));

        System.out.println("Fetched Store: " + store);


        return StoreMapper.toDTO(store);
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

        return stockPage.map(StockMapper::toDTO).getContent();
    }

    @Override
    public int getTotalStockCount(Long storeId) {
        return stockRepository.getTotalStockCountByStoreId(storeId);
    }
}
