package com.backend.Lakshya.dto;

import com.backend.Lakshya.model.TransactionAction;
import java.util.Date;

public class TransactionDTO {
    private Long id;
    private Long shopId;
    private String shopName;
    private String productName;
    private long quantity;
    private TransactionAction action;
    private Date lastUpdatedDate;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public TransactionAction getAction() { return action; }
    public void setAction(TransactionAction action) { this.action = action; }

    public Date getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(Date lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
}
