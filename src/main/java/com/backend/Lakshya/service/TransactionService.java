package com.backend.Lakshya.service;

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
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Transaction txn = new Transaction();
        txn.setShop(shop);
        txn.setProductName(productName);
        txn.setQuantity(quantity);
        txn.setAction(TransactionAction.STOCK_IN);

        transactionRepo.save(txn);

        Inventory updated = inventoryService.increaseStock(shopId, productName, quantity, price);

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setTransactionId(txn.getId());
        response.setShopId(shop.getShopId());
        response.setShopName(shop.getShopName());
        response.setProductName(productName);
        response.setQuantity(quantity);
        response.setAction("STOCK_IN");
        response.setLastUpdatedDate(txn.getLastUpdatedDate());
        response.setUpdatedStock(updated.getQuantity());

        return response;
    }

    // ---------- SALES ----------
    @Transactional
    public TransactionResponseDTO sale(Long shopId, String productName, long quantity) {
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Transaction txn = new Transaction();
        txn.setShop(shop);
        txn.setProductName(productName);
        txn.setQuantity(quantity);
        txn.setAction(TransactionAction.SALES);

        transactionRepo.save(txn);

        Inventory updated = inventoryService.decreaseStock(shopId, productName, quantity);

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
                .orElseThrow(() -> new RuntimeException("Source shop not found"));
        Shop destShop = shopRepo.findById(destShopId)
                .orElseThrow(() -> new RuntimeException("Destination shop not found"));

        // --- Source transaction ---
        Transaction sourceTxn = new Transaction();
        sourceTxn.setShop(sourceShop);
        sourceTxn.setProductName(productName);
        sourceTxn.setQuantity(quantity);
        sourceTxn.setAction(TransactionAction.TRANSFER);
        sourceTxn.setTransferToShop(destShop);
        transactionRepo.save(sourceTxn);

        Inventory sourceUpdated = inventoryService.decreaseStock(sourceShopId, productName, quantity);

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
        destTxn.setAction(TransactionAction.STOCK_IN);
        transactionRepo.save(destTxn);

        Inventory destUpdated = inventoryService.increaseStock(destShopId, productName, quantity, 0);

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
