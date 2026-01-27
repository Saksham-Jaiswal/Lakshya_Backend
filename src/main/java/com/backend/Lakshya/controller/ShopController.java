package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.Role;
import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.repository.ShopRepository;
import com.backend.Lakshya.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopRepository shopRepo;
    private final UserRepository userRepo;

    public ShopController(ShopRepository shopRepo,UserRepository userRepo) {
        this.shopRepo = shopRepo;
        this.userRepo=userRepo;
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
    @GetMapping("/salesperson/{id}")
    public Shop getShopBySalesperson(@PathVariable Long id) {
        return shopRepo.findBySalesperson_UserId(id);
    }
    @PutMapping("/{shopId}/assign/{salespersonId}")
    public ResponseEntity<Shop> assignSalesperson(@PathVariable Long shopId, @PathVariable Long salespersonId) {
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with ID: " + shopId));

        com.backend.Lakshya.model.User salesperson = userRepo.findById(salespersonId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + salespersonId));

        // --- FIXED ENUM COMPARISON ---
        // We compare the Enum object directly, not a String
        if (salesperson.getRole() != Role.SALESPERSON) {
            throw new RuntimeException("User is not a salesperson. Current role: " + salesperson.getRole());
        }

        shop.setSalesperson(salesperson);
        Shop updatedShop = shopRepo.save(shop);

        return ResponseEntity.ok(updatedShop);
    }
}
