package org.fawry.store.controllers;


import org.fawry.store.dtos.stock.StockConsumeRequestDTO;
import org.fawry.store.dtos.stock.StockConsumeResponseDTO;
import org.fawry.store.dtos.stock.StockRequestDTO;
import org.fawry.store.dtos.stock.StockResponseDTO;
import org.fawry.store.services.stock.StockService;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/availability")
    public ResponseEntity<Long> checkProductAvailability(
            @RequestParam Long productId,
            @RequestParam int quantity) {
        Long isAvailable = stockService.isProductAvailable(productId, quantity);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/createStock")
    public ResponseEntity<StockResponseDTO> createStock(
            @RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO stockResponseDTO = stockService.createStock(stockRequestDTO);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @PostMapping("/addStock")
    public ResponseEntity<StockResponseDTO> addStock(
            @RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO stockResponseDTO = stockService.addStock(stockRequestDTO);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @DeleteMapping("/{stockId}/deleteStock")
    public void deleteStock(@PathVariable Long stockId) {
        stockService.deleteStockById(stockId);
    }

    @PostMapping("/consume")
    public ResponseEntity<List<StockConsumeResponseDTO>> consumeStock(
            @RequestBody StockConsumeRequestDTO stockConsumeRequestDTO
    ) {
        List<StockConsumeResponseDTO> result = stockService.consumeStock(stockConsumeRequestDTO);
        return ResponseEntity.ok(result);
    }
}
