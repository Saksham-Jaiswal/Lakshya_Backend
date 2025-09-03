package com.backend.Lakshya.mapper;

import com.backend.Lakshya.dto.TransactionDTO;
import com.backend.Lakshya.model.Transaction;

public class TransactionMapper {
    public static TransactionDTO toDTO(Transaction txn) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(txn.getId());
        dto.setShopId(txn.getShop().getShopId());
        dto.setShopName(txn.getShop().getShopName());
        dto.setProductName(txn.getProductName());
        dto.setQuantity(txn.getQuantity());
        dto.setAction(txn.getAction());
        dto.setLastUpdatedDate(txn.getLastUpdatedDate());
        return dto;
    }
}
