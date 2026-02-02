package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Transaction;
import com.backend.Lakshya.model.TransactionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByShop_ShopId(Long shopId); // navigate through relation
    // 1. Calculate Revenue: SUM(price * quantity) where action is SALES
    @Query("SELECT COALESCE(SUM(t.price * t.quantity), 0) FROM Transaction t " +
            "WHERE t.shop.shopId = :shopId " +
            "AND t.action = :action " +
            "AND t.lastUpdatedDate BETWEEN :startDate AND :endDate")
    Double getRevenueByDateRange(
            @Param("shopId") Long shopId,
            @Param("action") TransactionAction action,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    // 2. Calculate Items Sold: SUM(quantity) where action is SALES
    @Query("SELECT COALESCE(SUM(t.quantity), 0) FROM Transaction t " +
            "WHERE t.shop.shopId = :shopId " +
            "AND t.action = :action " +
            "AND t.lastUpdatedDate BETWEEN :startDate AND :endDate")
    Long getItemsSoldByDateRange(
            @Param("shopId") Long shopId,
            @Param("action") TransactionAction action,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
