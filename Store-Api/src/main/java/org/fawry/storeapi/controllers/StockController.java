package org.fawry.storeapi.controllers;


import org.fawry.storeapi.dtos.stock.StockRequestDTO;
import org.fawry.storeapi.dtos.stock.StockResponseDTO;
import org.fawry.storeapi.services.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
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


    @DeleteMapping("/{stockId}/deleteStock")
    public void deleteStock(@PathVariable Long stockId)
    {
        stockService.deleteStockById(stockId);
    }

}

