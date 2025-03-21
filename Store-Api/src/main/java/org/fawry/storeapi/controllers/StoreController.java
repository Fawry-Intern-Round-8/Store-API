package org.fawry.storeapi.controllers;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.dtos.StoreDTO;
import org.fawry.storeapi.dtos.StoreRequestDTO;
import org.fawry.storeapi.dtos.StoreResponseDTO;
import org.fawry.storeapi.services.store.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
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
            @RequestBody StoreRequestDTO storeRequestDTO)
    {
        return storeService.createStore(storeRequestDTO.getName(),storeRequestDTO.getAddress()
                ,storeRequestDTO.getLongitude(),storeRequestDTO.getLatitude());
    }

    @PostMapping("/{storeId}/updateStore")
    public StoreResponseDTO updateStore(
            @PathVariable Long storeId,
            @RequestBody StoreRequestDTO storeRequestDTO)
    {
        return storeService.updateStore(storeId, storeRequestDTO.getName(),storeRequestDTO.getAddress()
                ,storeRequestDTO.getLongitude(),storeRequestDTO.getLatitude());
    }
    @DeleteMapping("/{storeId}/deleteStore")
    public void deleteStore(@PathVariable Long storeId)
    {
        storeService.deleteStoreById(storeId);
    }
    @GetMapping("/{storeId}/getTotalStocks")
    public int getTotalStockByStoreId(@PathVariable Long storeId) {
        return storeService.getTotalStockCount(storeId);
    }


}

