package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
