package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.*;
import com.backend.Lakshya.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // ---------- STOCK IN ----------
    @PostMapping("/stock-in")
    public ResponseEntity<TransactionResponseDTO> stockIn(@RequestBody StockInRequestDTO request) {
        TransactionResponseDTO response = transactionService.stockIn(
                request.getShopId(),
                request.getProductName(),
                request.getQuantity(),
                request.getPrice()
        );
        return ResponseEntity.ok(response);
    }

    // ---------- SALE ----------
    @PostMapping("/sale")
    public ResponseEntity<TransactionResponseDTO> sale(@RequestBody SaleRequestDTO request) {
        TransactionResponseDTO response = transactionService.sale(
                request.getShopId(),
                request.getProductName(),
                request.getQuantity()
        );
        return ResponseEntity.ok(response);
    }

    // ---------- TRANSFER ----------
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody TransferRequestDTO request) {
        TransferResponseDTO response = transactionService.transfer(
                request.getSourceShopId(),
                request.getDestShopId(),
                request.getProductName(),
                request.getQuantity()
        );
        return ResponseEntity.ok(response);
    }
}
