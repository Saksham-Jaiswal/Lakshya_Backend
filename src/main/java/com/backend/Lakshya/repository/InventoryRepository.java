package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByShop_ShopId(Long shopId); // this would give you the complete current inventory of the shop
    Inventory findByShop_ShopIdAndProductName(Long shopId, String productName);// this would give you the stock of a given product in a shop
    // Finds all inventory items for a shop where quantity is strictly less than 'threshold'
    List<Inventory> findByShop_ShopIdAndQuantityLessThan(Long shopId, long threshold);
}
