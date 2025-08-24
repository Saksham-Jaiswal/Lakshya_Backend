package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByOwner_UserId(Long ownerId);
    Shop findBySalesperson_UserId(Long salespersonId);
}
