package org.fawry.store.controllers;

import org.fawry.store.dtos.stock.StockDTO;
import org.fawry.store.dtos.store.StoreDTO;
import org.fawry.store.dtos.store.StoreRequestDTO;
import org.fawry.store.dtos.store.StoreResponseDTO;
import org.fawry.store.services.store.StoreService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/getAllStores")
    public List<StoreResponseDTO> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{storeId}/stocks")
    public List<StockDTO> getAllStocksForStore(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return storeService.getAllStocksForStore(storeId, page, size);
    }

    @GetMapping("/{storeId}")
    public StoreDTO getStoreById(
            @PathVariable Long storeId) {
        return storeService.findStoreById(storeId);
    }

    @PostMapping("/createStore")
    public StoreResponseDTO createStore(
            @RequestBody StoreRequestDTO storeRequestDTO) {
        return storeService.createStore(storeRequestDTO.getName(), storeRequestDTO.getAddress()
                , storeRequestDTO.getLongitude(), storeRequestDTO.getLatitude());
    }

    @PostMapping("/{storeId}/updateStore")
    public StoreResponseDTO updateStore(
            @PathVariable Long storeId,
            @RequestBody StoreRequestDTO storeRequestDTO) {
        return storeService.updateStore(storeId, storeRequestDTO.getName(), storeRequestDTO.getAddress()
                , storeRequestDTO.getLongitude(), storeRequestDTO.getLatitude());
    }

    @DeleteMapping("/{storeId}/deleteStore")
    public void deleteStore(@PathVariable Long storeId) {
        storeService.deleteStoreById(storeId);
    }

    @GetMapping("/{storeId}/getTotalStocks")
    public int getTotalStockByStoreId(@PathVariable Long storeId) {
        return storeService.getTotalStockCount(storeId);
    }
}
