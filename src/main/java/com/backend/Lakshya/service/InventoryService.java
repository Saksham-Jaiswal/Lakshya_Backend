package com.backend.Lakshya.service;

import com.backend.Lakshya.model.Inventory;
import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.repository.InventoryRepository;
import com.backend.Lakshya.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//only inventory CRUD & stock updates.

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final ShopRepository shopRepo;

    public InventoryService(InventoryRepository inventoryRepo,
                            ShopRepository shopRepo) {
        this.inventoryRepo = inventoryRepo;
        this.shopRepo = shopRepo;
    }

    public List<Inventory> getInventoryByShop(Long shopId) {
        return inventoryRepo.findByShop_ShopId(shopId);
    }

    @Transactional
    public Inventory increaseStock(Long shopId, String productName, long quantity, double price) {
        Inventory inventory = getOrCreateInventory(shopId, productName);
        inventory.setQuantity(inventory.getQuantity() + quantity);

        // Optional: update price if given
        if (price > 0) {
            inventory.setPrice(price);
        }

        return inventoryRepo.save(inventory);
    }

    @Transactional
    public Inventory decreaseStock(Long shopId, String productName, long quantity) {
        Inventory inventory = getOrCreateInventory(shopId, productName);

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + productName);
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        return inventoryRepo.save(inventory);
    }

    private Inventory getOrCreateInventory(Long shopId, String productName) {
        Inventory inventory = inventoryRepo.findByShop_ShopIdAndProductName(shopId, productName);

        if (inventory == null) {
            Shop shop = shopRepo.findById(shopId)
                    .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));

            inventory = new Inventory();
            inventory.setShop(shop);
            inventory.setProductName(productName);
            inventory.setQuantity(0);
            inventory.setPrice(0);
        }

        return inventory;
    }
}
