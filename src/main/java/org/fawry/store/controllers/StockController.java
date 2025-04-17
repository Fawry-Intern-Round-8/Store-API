package org.fawry.store.controllers;


import org.fawry.store.dtos.stock.StockConsumeRequestDTO;
import org.fawry.store.dtos.stock.StockConsumeResponseDTO;
import org.fawry.store.dtos.stock.StockRequestDTO;
import org.fawry.store.dtos.stock.StockResponseDTO;
import org.fawry.store.services.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/availability")
    public ResponseEntity<Long> checkProductAvailability(@RequestParam Long productId, @RequestParam int quantity) {
        Long isAvailable = stockService.isProductAvailable(productId, quantity);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/getAllStocks")
    public ResponseEntity<List<StockResponseDTO>> getAllStocks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<StockResponseDTO> stocks = stockService.getAllStocks(page, size);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/getStockById/{stockId}")
    public ResponseEntity<StockResponseDTO> getStockById(@PathVariable Long stockId) {
        StockResponseDTO stockResponseDTO = stockService.getStockById(stockId);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @PostMapping("/createStock")
    public ResponseEntity<StockResponseDTO> createStock(@RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO stockResponseDTO = stockService.createStock(stockRequestDTO);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @PostMapping("/addStock")
    public ResponseEntity<StockResponseDTO> addStock(@RequestBody StockRequestDTO stockRequestDTO) {
        StockResponseDTO stockResponseDTO = stockService.addStock(stockRequestDTO);
        return ResponseEntity.ok(stockResponseDTO);
    }

    @DeleteMapping("/{stockId}/deleteStock")
    public void deleteStock(@PathVariable Long stockId) {
        stockService.deleteStockById(stockId);
    }

    @PostMapping("/consume")
    public ResponseEntity<List<StockConsumeResponseDTO>> consumeStock(@RequestBody StockConsumeRequestDTO stockConsumeRequestDTO) {
        List<StockConsumeResponseDTO> result = stockService.consumeStock(stockConsumeRequestDTO);
        return ResponseEntity.ok(result);
    }
}

