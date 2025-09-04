package com.backend.Lakshya.service;

import com.backend.Lakshya.customException.InventoryUpdateException;
import com.backend.Lakshya.customException.ShopNotFoundException;
import com.backend.Lakshya.dto.TransactionResponseDTO;
import com.backend.Lakshya.dto.TransferResponseDTO;
import com.backend.Lakshya.model.*;
import com.backend.Lakshya.repository.InventoryRepository;
import com.backend.Lakshya.repository.ShopRepository;
import com.backend.Lakshya.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final InventoryService inventoryService;
    private final ShopRepository shopRepo;

    public TransactionService(TransactionRepository transactionRepo,
                              InventoryService inventoryService,
                              ShopRepository shopRepo) {
        this.transactionRepo = transactionRepo;
        this.inventoryService = inventoryService;
        this.shopRepo = shopRepo;
    }

    // ---------- STOCK IN ----------
    @Transactional
    public TransactionResponseDTO stockIn(Long shopId, String productName, long quantity, double price) {
        // Validate shop existence
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException("Shop not found with ID: " + shopId));

        // Create and save transaction
        Transaction txn = new Transaction();
        txn.setShop(shop);
        txn.setProductName(productName);
        txn.setQuantity(quantity);
        txn.setAction(TransactionAction.STOCK_IN);

        transactionRepo.save(txn);// because we are doing a database txn here we used @Transactional annotation so that either this methods completely succeed or rollback

        // Update inventory
        Inventory updated;
        try {
            updated = inventoryService.increaseStock(shopId, productName, quantity, price);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName + " in shop ID: " + shopId);
        }

        //creating Response
        TransactionResponseDTO response = new TransactionResponseDTO();
        //The transactionId and lastUpdatedDate are generated after saving the Transaction object to the database with transactionRepo.save(txn).
        response.setTransactionId(txn.getId());
        response.setLastUpdatedDate(txn.getLastUpdatedDate());
        response.setShopId(shopId);
        response.setShopName(shop.getShopName());
        response.setProductName(productName);
        response.setQuantity(quantity);
        response.setAction("STOCK_IN");
        response.setUpdatedStock(updated.getQuantity());

        return response;
    }

    // ---------- SALES ----------
    @Transactional
    public TransactionResponseDTO sale(Long shopId, String productName, long quantity) {
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException("Shop not found with ID: " + shopId));

        Transaction txn = new Transaction();
        txn.setShop(shop);
        txn.setProductName(productName);
        txn.setQuantity(quantity);
        txn.setAction(TransactionAction.SALES);

        transactionRepo.save(txn);

        // Update inventory
        Inventory updated;
        try {
            updated = inventoryService.decreaseStock(shopId, productName, quantity);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName + " in shop ID: \n" + shopId + e);
        }

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setTransactionId(txn.getId());
        response.setShopId(shop.getShopId());
        response.setShopName(shop.getShopName());
        response.setProductName(productName);
        response.setQuantity(quantity);
        response.setAction("SALES");
        response.setLastUpdatedDate(txn.getLastUpdatedDate());
        response.setUpdatedStock(updated.getQuantity());

        return response;
    }

    // ---------- TRANSFER ----------
    @Transactional
    public TransferResponseDTO transfer(Long sourceShopId, Long destShopId, String productName, long quantity) {
        Shop sourceShop = shopRepo.findById(sourceShopId)
                .orElseThrow(() -> new ShopNotFoundException("Shop not found with ID: " + sourceShopId));
        Shop destShop = shopRepo.findById(destShopId)
                .orElseThrow(() ->  new ShopNotFoundException("Shop not found with ID: " + destShopId));

        // --- Source transaction ---
        Transaction sourceTxn = new Transaction();
        sourceTxn.setShop(sourceShop);
        sourceTxn.setProductName(productName);
        sourceTxn.setQuantity(quantity);
        sourceTxn.setAction(TransactionAction.TRANSFER_OUT);
        sourceTxn.setTransferToShop(destShop);
        transactionRepo.save(sourceTxn);

        Inventory sourceUpdated;
        try {
            sourceUpdated = inventoryService.decreaseStock(sourceShopId, productName, quantity);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName + " in shop ID: \n" + sourceShopId+e);
        }

        TransactionResponseDTO sourceResponse = new TransactionResponseDTO();
        sourceResponse.setTransactionId(sourceTxn.getId());
        sourceResponse.setShopId(sourceShop.getShopId());
        sourceResponse.setShopName(sourceShop.getShopName());
        sourceResponse.setProductName(productName);
        sourceResponse.setQuantity(quantity);
        sourceResponse.setAction("TRANSFER_OUT");
        sourceResponse.setTransferToShopName(destShop.getShopName());
        sourceResponse.setLastUpdatedDate(sourceTxn.getLastUpdatedDate());
        sourceResponse.setUpdatedStock(sourceUpdated.getQuantity());

        // --- Destination transaction ---
        Transaction destTxn = new Transaction();
        destTxn.setShop(destShop);
        destTxn.setProductName(productName);
        destTxn.setQuantity(quantity);
        destTxn.setAction(TransactionAction.TRANSFER_IN);
        transactionRepo.save(destTxn);

        Inventory destUpdated;

        try {
            destUpdated = inventoryService.increaseStock(destShopId, productName, quantity,0);
        } catch (Exception e) {
            throw new InventoryUpdateException("Failed to update inventory for product: " + productName + " in shop ID: " + destShopId);
        }


        TransactionResponseDTO destResponse = new TransactionResponseDTO();
        destResponse.setTransactionId(destTxn.getId());
        destResponse.setShopId(destShop.getShopId());
        destResponse.setShopName(destShop.getShopName());
        destResponse.setProductName(productName);
        destResponse.setQuantity(quantity);
        destResponse.setAction("TRANSFER_IN");
        destResponse.setLastUpdatedDate(destTxn.getLastUpdatedDate());
        destResponse.setUpdatedStock(destUpdated.getQuantity());

        // --- Final response ---
        TransferResponseDTO transferResponse = new TransferResponseDTO();
        transferResponse.setSourceTransaction(sourceResponse);
        transferResponse.setDestinationTransaction(destResponse);

        return transferResponse;
    }
}
