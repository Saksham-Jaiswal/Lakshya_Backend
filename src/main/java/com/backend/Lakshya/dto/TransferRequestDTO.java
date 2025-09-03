package com.backend.Lakshya.dto;

public class TransferRequestDTO {
    private Long sourceShopId;
    private Long destShopId;
    private String productName;
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
