package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.Product;
import com.backend.Lakshya.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    // Public or Authenticated: List all available products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // Owner Only: Create a new product definition
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        if (productRepo.existsByName(product.getName())) {
            throw new RuntimeException("Product with this name already exists");
        }
        return productRepo.save(product);
    }
}