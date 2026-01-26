package com.backend.Lakshya.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequestDTO {
    @NotNull(message = "Shop ID is required")
    private Long sourceShopId;
    @NotNull(message = "Shop ID is required")
    private Long destShopId;
    @NotEmpty(message = "Product name cannot be empty")
    private String productName;
    @Positive(message = "Quantity must be positive")
    private long quantity;

    // Getters & Setters
    public Long getSourceShopId() { return sourceShopId; }
    public void setSourceShopId(Long sourceShopId) { this.sourceShopId = sourceShopId; }

    public Long getDestShopId() { return destShopId; }
    public void setDestShopId(Long destShopId) { this.destShopId = destShopId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }
}
