package org.fawry.store.controllers;



import org.fawry.store.dtos.transactionshistory.TransactionsHistoryDTO;
import org.fawry.store.services.stockhistory.StockTransactionsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions-history")
public class StockTransactionsHistoryController {

    @Autowired
    StockTransactionsHistoryService stockTransactionsHistoryService;
    @GetMapping
    public ResponseEntity<List<TransactionsHistoryDTO>> getAllTransactions(){
         return ResponseEntity.ok(stockTransactionsHistoryService.getAllTransactions());
    }
}
