package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByShop_ShopId(Long shopId); // navigate through relation
}
