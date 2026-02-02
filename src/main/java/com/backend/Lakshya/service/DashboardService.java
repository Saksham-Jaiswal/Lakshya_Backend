package com.backend.Lakshya.service;

import com.backend.Lakshya.model.TransactionAction;
import com.backend.Lakshya.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Update the method signature to accept the date string
    public Map<String, Object> getShopStats(Long shopId, String period, String dateStr) {
        Date start;
        LocalDate today = LocalDate.now();
        Date end = Date.from(
                today.atTime(LocalTime.MAX)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        try {
            switch (period.toLowerCase()) {
                case "today":
                    start = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    break;
                case "week":
                    start = Date.from(today.minusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
                    break;
                case "month":
                    start = Date.from(today.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
                    break;
                case "year":
                    start = Date.from(today.minusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant());
                    break;
                case "custom": // <--- NEW LOGIC
                    if (dateStr != null) {
                        // Parse "yyyy-MM-dd"
                        LocalDate customDate = LocalDate.parse(dateStr);
                        // Start: 00:00:00 of that day
                        start = Date.from(customDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        // End: 23:59:59 of that day
                        end = Date.from(customDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
                    } else {
                        // Fallback to today if date missing
                        start = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    }
                    break;
                default: // "all"
                    start = Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                    break;
            }

            Double revenue = transactionRepository.getRevenueByDateRange(shopId, TransactionAction.SALES, start, end);
            Long itemsSold = transactionRepository.getItemsSoldByDateRange(shopId, TransactionAction.SALES, start, end);

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalRevenue", revenue);
            stats.put("totalItemsSold", itemsSold);

            return stats;

        } catch (Exception e) {
            throw new RuntimeException("Error calculating stats: " + e.getMessage());
        }
    }

}