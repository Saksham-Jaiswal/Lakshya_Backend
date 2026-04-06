package com.backend.Lakshya.service;

import com.backend.Lakshya.customException.BusinessException;
import com.backend.Lakshya.dto.ShopDTO;
import com.backend.Lakshya.mapper.ShopMapper;
import com.backend.Lakshya.model.Role;
import com.backend.Lakshya.model.Shop;
import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.ShopRepository;
import com.backend.Lakshya.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ShopService {
    private final ShopRepository shopRepo;
    private final UserRepository userRepo;

    public ShopService(ShopRepository shopRepo, UserRepository userRepo) {
        this.shopRepo = shopRepo;
        this.userRepo = userRepo;
    }
    public List<ShopDTO> getShops()
    {
        return shopRepo.findAll().stream().map(ShopMapper::toDTO).collect(Collectors.toList());
    }
    @Transactional
    public ShopDTO createShop(Shop shop) {
        try {
            Shop savedShop = shopRepo.save(shop);
            return ShopMapper.toDTO(savedShop);

        } catch (DataIntegrityViolationException ex) {
            // Handles PostgreSQL unique constraint violations
            throw new BusinessException(
                    "Shop already exists or violates a unique constraint");
        }
    }

    public List<ShopDTO> getShopsByOwner(long ownerId)
    {
        return shopRepo.findByOwner_UserId(ownerId).stream().map(ShopMapper::toDTO).collect(Collectors.toList());
    }

    public ShopDTO getShopBySalesperson(long id)
    {
        return ShopMapper.toDTO(
                shopRepo.findBySalesperson_UserId(id));
    }

    public ShopDTO assignSalesperson(Long shopId, Long salespersonId) {

        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() ->
                        new RuntimeException("Shop not found with ID: " + shopId));

        User salesperson = userRepo.findById(salespersonId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + salespersonId));

        if (salesperson.getRole() != Role.SALESPERSON) {
            throw new RuntimeException(
                    "User is not a SALESPERSON. Current role: " + salesperson.getRole()
            );
        }

        shop.setSalesperson(salesperson);
        return ShopMapper.toDTO(shopRepo.save(shop));
    }

    public ShopDTO updateShop(Long shopId, ShopDTO shopDetails) {
        // 1. Find the existing shop
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));

        // 2. Update the fields (Right now, just the name)
        if (shopDetails.getShopName() != null && !shopDetails.getShopName().trim().isEmpty()) {
            shop.setShopName(shopDetails.getShopName());
        }

        // 3. Save to database
        Shop updatedShop = shopRepo.save(shop);

        // 4. Convert back to DTO and return (assuming you have a mapping method)
        // Replace this with however your service currently maps Entities to DTOs!
        return ShopMapper.toDTO(updatedShop);
    }







}
