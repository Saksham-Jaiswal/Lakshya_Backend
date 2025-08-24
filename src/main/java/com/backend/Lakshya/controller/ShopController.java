package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.repository.ShopRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopRepository shopRepo;

    public ShopController(ShopRepository shopRepo) {
        this.shopRepo = shopRepo;
    }

    @GetMapping
    public List<Shop> getAllShops() {
        return shopRepo.findAll();
    }

    @PostMapping
    public Shop createShop(@RequestBody Shop shop) {
        return shopRepo.save(shop);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Shop> getShopsByOwner(@PathVariable Long ownerId) {
        return shopRepo.findByOwner_UserId(ownerId);
    }
}
