package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.TransactionDTO;
import com.backend.Lakshya.mapper.TransactionMapper;
import com.backend.Lakshya.repository.TransactionsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionsRepository transactionRepo;

    public TransactionsController(TransactionsRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @GetMapping("/shop/{shopId}")
    public List<TransactionDTO> getTransactionsByShop(@PathVariable Long shopId) {
        return transactionRepo.findByShop_ShopId(shopId)
                .stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}
