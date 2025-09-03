package com.backend.Lakshya.dto;

import java.util.Date;

public class TransactionResponseDTO {
    private Long transactionId;
    private Long shopId;
    private String shopName;
    private String productName;
    private long quantity;
    private String action;
    private String transferToShopName; // only for transfer-out
    private Date lastUpdatedDate;
    private long updatedStock;

    // Getters & Setters
    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getTransferToShopName() { return transferToShopName; }
    public void setTransferToShopName(String transferToShopName) { this.transferToShopName = transferToShopName; }

    public Date getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(Date lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

    public long getUpdatedStock() { return updatedStock; }
    public void setUpdatedStock(long updatedStock) { this.updatedStock = updatedStock; }
}
