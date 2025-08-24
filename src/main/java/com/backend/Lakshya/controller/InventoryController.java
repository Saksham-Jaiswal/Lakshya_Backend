package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.InventoryDTO;
import com.backend.Lakshya.mapper.InventoryMapper;
import com.backend.Lakshya.model.Transactions;
import com.backend.Lakshya.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{shopId}")
    public List<InventoryDTO> getInventory(@PathVariable Long shopId) {
        return inventoryService.getInventoryByShop(shopId)
                .stream()
                .map(InventoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/{shopId}/transaction")
    public InventoryDTO handleTransaction(@PathVariable Long shopId, @RequestBody Transactions transaction) {
        return InventoryMapper.toDTO(inventoryService.handleTransaction(shopId, transaction));
    }
}
