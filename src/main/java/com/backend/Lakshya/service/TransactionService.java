package com.backend.Lakshya.service;

import com.backend.Lakshya.customException.InventoryUpdateException;
import com.backend.Lakshya.customException.ShopNotFoundException;
import com.backend.Lakshya.dto.TransactionResponseDTO;
import com.backend.Lakshya.dto.TransferResponseDTO;
import com.backend.Lakshya.model.*;
import com.backend.Lakshya.repository.InventoryRepository;
import com.backend.Lakshya.repository.ShopRepository;
import com.backend.Lakshya.util.TransactionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final InventoryService inventoryService;
    private final ShopRepository shopRepo;
    private final TransactionUtil transactionUtil;
    private final InventoryRepository inventoryRepository;

    public TransactionService(InventoryRepository inventoryRepository,
                              InventoryService inventoryService,
                              ShopRepository shopRepo,TransactionUtil transactionUtil) {
        this.inventoryService = inventoryService;
        this.shopRepo = shopRepo;
        this.transactionUtil=transactionUtil;
        this.inventoryRepository=inventoryRepository;
    }

    // ---------- STOCK IN ----------
    // because we are doing a database txn here we used @Transactional annotation so that either this methods completely succeed or rollback

    private Shop findShopById(Long shopId) {
        return shopRepo.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException("Shop not found with ID: " + shopId));
    }

    @Transactional
    public TransactionResponseDTO stockIn(Long shopId, String productName, long quantity, double price) {
        // Validate and finding shop
        Shop shop = findShopById(shopId);

        // Update inventory
        Inventory updated;
        try {
            updated = inventoryService.increaseStock(shopId, productName, quantity, price);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName +
                    " in shop ID: " + shopId + ". Cause: " + e.getMessage());
        }
        //creating Response
        TransactionResponseDTO response = transactionUtil.createAndSaveTransaction(shop, productName, quantity,
                TransactionAction.STOCK_IN, null);

        response.setUpdatedStock(updated.getQuantity());
        return response;
    }

    // ---------- SALES ----------
    @Transactional
    public TransactionResponseDTO sale(Long shopId, String productName, long quantity) {
        Shop shop = findShopById(shopId);

        Inventory updated;
        try {
            updated = inventoryService.decreaseStock(shopId, productName, quantity);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName +
                    " in shop ID: " + shopId + ". Cause: " + e.getMessage());
        }

        TransactionResponseDTO response = transactionUtil.createAndSaveTransaction(shop, productName, quantity,
                TransactionAction.SALES, null);
        response.setUpdatedStock(updated.getQuantity());
        return response;
    }

    // ---------- TRANSFER ----------
    @Transactional
    public TransferResponseDTO transfer(Long sourceShopId, Long destShopId, String productName, long quantity) {
        Shop sourceShop = findShopById(sourceShopId);
        Shop destShop = findShopById(destShopId);

        Inventory sourceUpdated;
        try {
            sourceUpdated = inventoryService.decreaseStock(sourceShopId, productName, quantity);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName +
                    " in shop ID: " + sourceShopId + ". Cause: " + e.getMessage());
        }

        // --- Source transaction ---
        TransactionResponseDTO sourceResponse = transactionUtil.createAndSaveTransaction(sourceShop, productName, quantity,
                TransactionAction.TRANSFER_OUT, destShop);
        sourceResponse.setUpdatedStock(sourceUpdated.getQuantity());

        Inventory destUpdated;
        try {
            destUpdated = inventoryService.increaseStock(destShopId, productName, quantity, sourceUpdated.getPrice());
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName +
                    " in shop ID: " + destShopId + ". Cause: " + e.getMessage());
        }
        // --- Destination transaction ---
        TransactionResponseDTO destResponse = transactionUtil.createAndSaveTransaction(destShop, productName, quantity,
                TransactionAction.TRANSFER_IN,sourceShop);
        destResponse.setUpdatedStock(destUpdated.getQuantity());

        // --- Final response ---
        TransferResponseDTO transferResponse = new TransferResponseDTO();
        transferResponse.setSourceTransaction(sourceResponse);
        transferResponse.setDestinationTransaction(destResponse);

        return transferResponse;
    }
}
