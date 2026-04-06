package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.ShopDTO;
import com.backend.Lakshya.model.Role;
import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.repository.ShopRepository;
import com.backend.Lakshya.repository.UserRepository;
import com.backend.Lakshya.service.ShopService;
import com.backend.Lakshya.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {


    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        List<ShopDTO> shops=shopService.getShops();
        return ResponseEntity.ok(shops);
    }

    @PostMapping
    public ResponseEntity<ShopDTO> createAShop(@RequestBody Shop shop) {
        ShopDTO savedShop=shopService.createShop(shop);
        return ResponseEntity.ok(savedShop);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ShopDTO>> getAllShopsByOwner(@PathVariable Long ownerId) {
        List<ShopDTO> shops=shopService.getShopsByOwner(ownerId);
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/salesperson/{id}")
    public ResponseEntity<ShopDTO> getAShopBySalesperson(@PathVariable Long id) {
        ShopDTO smShop=shopService.getShopBySalesperson(id);
        return ResponseEntity.ok(smShop);
    }
    @PutMapping("/{shopId}/assign/{salespersonId}")
    public ResponseEntity<ShopDTO> assignSalesperson(
            @PathVariable Long shopId,
            @PathVariable Long salespersonId) {

        ShopDTO updatedShop = shopService.assignSalesperson(shopId, salespersonId);
        return ResponseEntity.ok(updatedShop);
    }
    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDTO> updateShop(
            @PathVariable Long shopId,
            @RequestBody ShopDTO shopDetails) {

        try {
            ShopDTO updatedShop = shopService.updateShop(shopId, shopDetails);
            return ResponseEntity.ok(updatedShop);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
