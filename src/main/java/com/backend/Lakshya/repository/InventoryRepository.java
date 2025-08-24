package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByShop_ShopId(Long shopId);
    Inventory findByShop_ShopIdAndProductName(Long shopId, String productName);
}
