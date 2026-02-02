package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.InventoryDTO;
import com.backend.Lakshya.service.DashboardService;
import com.backend.Lakshya.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    private InventoryService inventoryService;

    // Example URL: GET /api/dashboard/stats/1?period=week
    @GetMapping("/stats/{shopId}")
    public ResponseEntity<?> getShopStats(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(required = false) String date
    ) {
//        System.out.println("Received Request -> ShopID: " + shopId + " | Period: " + period);
        return ResponseEntity.ok(dashboardService.getShopStats(shopId, period,date));
    }
    @GetMapping("/alerts/{shopId}")
    public ResponseEntity<List<InventoryDTO>> getLowStockAlerts(@PathVariable Long shopId, @RequestParam(defaultValue = "10") long threshold )// Default threshold is 10
    {
        List<InventoryDTO> inv=inventoryService.getLowStockAlerts(shopId,threshold);
        return ResponseEntity.ok(inv);
    }
}