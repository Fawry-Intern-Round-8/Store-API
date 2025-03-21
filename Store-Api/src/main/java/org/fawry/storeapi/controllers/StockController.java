package org.fawry.storeapi.controllers;


import org.fawry.storeapi.services.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkProductAvailability(
            @RequestParam Long productId,
            @RequestParam int quantity) {
        boolean isAvailable = stockService.isProductAvailable(productId, quantity);
        return ResponseEntity.ok(isAvailable);
    }
}

