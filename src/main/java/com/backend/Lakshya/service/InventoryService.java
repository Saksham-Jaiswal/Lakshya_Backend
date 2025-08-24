package com.backend.Lakshya.service;

import com.backend.Lakshya.model.*;
import com.backend.Lakshya.repository.InventoryRepository;
import com.backend.Lakshya.repository.ShopRepository;
import com.backend.Lakshya.repository.TransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final TransactionsRepository transactionRepo;
    private final ShopRepository shopRepo;

    public InventoryService(InventoryRepository inventoryRepo,
                            TransactionsRepository transactionRepo,
                            ShopRepository shopRepo) {
        this.inventoryRepo = inventoryRepo;
        this.transactionRepo = transactionRepo;
        this.shopRepo = shopRepo;
    }

    public List<Inventory> getInventoryByShop(Long shopId) {
        return inventoryRepo.findByShop_ShopId(shopId);
    }

    @Transactional
    public Inventory handleTransaction(Long shopId, Transactions transaction) {
        // fetch shop
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));

        // attach shop to transaction
        transaction.setShop(shop);

        // save transaction
        transactionRepo.save(transaction);

        // update inventory
        Inventory inventory = inventoryRepo.findByShop_ShopIdAndProductName(
                shopId,
                transaction.getProductName()
        );

        if (inventory == null) {
            inventory = new Inventory();
            inventory.setShop(shop);
            inventory.setProductName(transaction.getProductName());
            inventory.setQuantity(0);
            inventory.setPrice(0); // to be updated separately
        }

        switch (transaction.getAction()) {
            case STOCK_IN -> inventory.setQuantity(inventory.getQuantity() + transaction.getQuantity());
            case SALES -> inventory.setQuantity(inventory.getQuantity() - transaction.getQuantity());
            case TRANSFER -> inventory.setQuantity(inventory.getQuantity() - transaction.getQuantity());
        }

        return inventoryRepo.save(inventory);
    }
}
