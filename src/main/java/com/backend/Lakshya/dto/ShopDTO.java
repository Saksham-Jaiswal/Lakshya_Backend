package com.backend.Lakshya.dto;

public class ShopDTO {
    private Long shopId;
    private String shopName;
    private Long ownerId;
    private Long salespersonId;

    // Getters & Setters
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public Long getSalespersonId() { return salespersonId; }
    public void setSalespersonId(Long salespersonId) { this.salespersonId = salespersonId; }
}
