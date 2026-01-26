package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.InventoryDTO;
import com.backend.Lakshya.mapper.InventoryMapper;
import com.backend.Lakshya.model.Inventory;
import com.backend.Lakshya.model.Transaction;
import com.backend.Lakshya.model.TransactionAction;
import com.backend.Lakshya.repository.InventoryRepository;
import com.backend.Lakshya.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TransactionRepository transactionRepo;
    private final InventoryRepository inventoryRepo;

    public DashboardController(TransactionRepository transactionRepo,
                               InventoryRepository inventoryRepo) {
        this.transactionRepo = transactionRepo;
        this.inventoryRepo=inventoryRepo;
    }

    @GetMapping("/stats/{shopId}")
    public ResponseEntity<Map<String, Object>> getShopStats(@PathVariable Long shopId) {
        List<Transaction> transactions = transactionRepo.findByShop_ShopId(shopId);

        double totalRevenue = transactions.stream()
                .filter(t -> t.getAction() == TransactionAction.SALES)
                .mapToDouble(t -> t.getQuantity() * t.getPrice())
                .sum();

        long totalItemsSold = transactions.stream()
                .filter(t -> t.getAction() == TransactionAction.SALES)
                .mapToLong(Transaction::getQuantity)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("shopId", shopId);
        stats.put("totalRevenue", totalRevenue);
        stats.put("totalItemsSold", totalItemsSold);

        return ResponseEntity.ok(stats);
    }
    @GetMapping("/alerts/{shopId}")
    public ResponseEntity<List<InventoryDTO>> getLowStockAlerts(@PathVariable Long shopId, @RequestParam(defaultValue = "10") long threshold )// Default threshold is 10
     {
        List<Inventory> lowStockItems = inventoryRepo.findByShop_ShopIdAndQuantityLessThan(shopId, threshold);

        List<InventoryDTO> response = lowStockItems.stream()
                .map(InventoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}