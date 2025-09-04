package com.backend.Lakshya.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SaleRequestDTO {
    @NotNull(message = "Shop ID is required")
    private Long shopId;
    @NotEmpty(message = "Product name cannot be empty")
    private String productName;
    @Positive(message = "Quantity must be positive")
    private long quantity;

    // Getters & Setters
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }
}
