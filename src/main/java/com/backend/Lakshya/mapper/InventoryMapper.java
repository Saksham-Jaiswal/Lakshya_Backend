package com.backend.Lakshya.mapper;

import com.backend.Lakshya.dto.InventoryDTO;
import com.backend.Lakshya.model.Inventory;

public class InventoryMapper {
    public static InventoryDTO toDTO(Inventory inv) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inv.getId());
        dto.setShopId(inv.getShop().getShopId());
        dto.setShopName(inv.getShop().getShopName());
        dto.setProductName(inv.getProductName());
        dto.setQuantity(inv.getQuantity());
        dto.setPrice(inv.getPrice());
        dto.setLastUpdatedDate(inv.getLastUpdatedDate());
        return dto;
    }
}
