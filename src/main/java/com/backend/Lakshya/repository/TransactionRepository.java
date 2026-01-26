package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByShop_ShopId(Long shopId); // navigate through relation
}
