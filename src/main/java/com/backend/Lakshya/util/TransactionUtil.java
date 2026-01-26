package com.backend.Lakshya.util;

import com.backend.Lakshya.dto.TransactionResponseDTO;
import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.model.Transaction;
import com.backend.Lakshya.model.TransactionAction;
import com.backend.Lakshya.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtil {

    private final TransactionRepository transactionRepo;

    public TransactionUtil(TransactionRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public TransactionResponseDTO createAndSaveTransaction(Shop shop, String productName, long quantity,
                                                           TransactionAction action, Shop transferToShop) {
        // Create and save transaction
        Transaction txn = new Transaction();
        txn.setShop(shop);
        txn.setProductName(productName);
        txn.setQuantity(quantity);
        txn.setAction(action);
        if (transferToShop != null) {
            txn.setTransferToShop(transferToShop);
        }

        Transaction savedTxn = transactionRepo.save(txn);

        //creating Response
        TransactionResponseDTO response = new TransactionResponseDTO();
        //The transactionId and lastUpdatedDate are generated after saving the Transaction object to the database with transactionRepo.save(txn).
        response.setTransactionId(savedTxn.getId());
        response.setLastUpdatedDate(savedTxn.getLastUpdatedDate());
        response.setShopId(shop.getShopId());
        response.setShopName(shop.getShopName());
        response.setProductName(productName);
        response.setQuantity(quantity);
        response.setAction(action.toString());
        if (transferToShop != null) {
            response.setTransferToShopName(transferToShop.getShopName());
        }
        return response;
    }
}