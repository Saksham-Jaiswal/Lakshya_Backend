package com.backend.Lakshya.mapper;

import com.backend.Lakshya.dto.ShopDTO;
import com.backend.Lakshya.model.Shop;

public class ShopMapper {
    public static ShopDTO toDTO(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setShopId(shop.getShopId());
        dto.setShopName(shop.getShopName());
        dto.setOwnerId(shop.getOwner().getUserId());
        dto.setSalespersonId(shop.getSalesperson().getUserId());
        return dto;
    }
}
