package com.backend.Lakshya.service;

import com.backend.Lakshya.customException.InventoryUpdateException;
import com.backend.Lakshya.customException.ShopNotFoundException;
import com.backend.Lakshya.model.Inventory;
import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.repository.InventoryRepository;
import com.backend.Lakshya.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final ShopRepository shopRepo;

    public InventoryService(InventoryRepository inventoryRepo, ShopRepository shopRepo) {
        this.inventoryRepo = inventoryRepo;
        this.shopRepo = shopRepo;
    }

    public List<Inventory> getInventoryByShop(Long shopId) {
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException("Shop not found with ID: " + shopId));
        return inventoryRepo.findByShop_ShopId(shopId);
    }

    @Transactional
    public Inventory increaseStock(Long shopId, String productName, long quantity, double price) {
        Inventory inventory = inventoryRepo.findByShop_ShopIdAndProductName(shopId, productName);
        if (inventory == null) {
            Shop shop = shopRepo.findById(shopId)
                    .orElseThrow(() -> new ShopNotFoundException("Shop not found with ID: " + shopId));
            inventory = new Inventory();
            inventory.setShop(shop);
            inventory.setProductName(productName);
            inventory.setQuantity(0);
            inventory.setPrice(0.0);
        }
        inventory.setQuantity(inventory.getQuantity() + quantity);
        if (price > 0) {
            inventory.setPrice(price);
        }
        return inventoryRepo.save(inventory);
    }

    @Transactional
    public Inventory decreaseStock(Long shopId, String productName, long quantity) {
        Inventory inventory = getExistingInventory(shopId, productName);
        if (inventory.getQuantity() < quantity) {
            throw new InventoryUpdateException("Insufficient stock for product: " + productName +
                    " in shop ID: " + shopId + ". Available: " + inventory.getQuantity());
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        return inventoryRepo.save(inventory);
    }

    private Inventory getExistingInventory(Long shopId, String productName) {
        Inventory inventory = inventoryRepo.findByShop_ShopIdAndProductName(shopId, productName);
        if (inventory == null) {
            throw new InventoryUpdateException("No inventory found for product: " + productName +
                    " in shop ID: " + shopId);
        }
        return inventory;
    }
}
/*
JPA/Hibernate Behavior:

If the Inventory object has an ID (i.e., it was loaded from the database), save updates the existing record.
If the Inventory object has no ID (i.e., it’s newly created), save creates a new record.


 */