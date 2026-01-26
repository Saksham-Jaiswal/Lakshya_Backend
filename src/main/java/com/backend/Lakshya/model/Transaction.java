package com.backend.Lakshya.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many transactions belong to one shop (the source shop for sales/transfer, or receiving shop for stock_in)
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id", nullable = false)
    private Shop shop;

    private String productName;
    private long quantity;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    @Enumerated(EnumType.STRING)
    private TransactionAction action; // SALES / STOCK_IN ETC..

    // For transfers, we need to know where the stock went
    @ManyToOne
    @JoinColumn(name = "transfer_to_shop_id", referencedColumnName = "shop_id")
    private Shop transferToShop;
    private double price; // Store the unit price here

    /*
      action values:
      1. SALES → quantity deducted
      2. STOCK_IN → quantity added
      3. TRANSFER → action=SALES (from source) + transferToShop filled (destination)
     */

    // Getters and setters
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Shop getShop() { return shop; }
    public void setShop(Shop shop) { this.shop = shop; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public Date getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(Date lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

    public TransactionAction getAction() { return action; }
    public void setAction(TransactionAction action) { this.action = action; }

    public Shop getTransferToShop() { return transferToShop; }
    public void setTransferToShop(Shop transferToShop) { this.transferToShop = transferToShop; }
}
