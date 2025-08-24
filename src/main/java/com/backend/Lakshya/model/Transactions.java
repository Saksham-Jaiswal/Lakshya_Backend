package com.backend.Lakshya.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name="transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many transactions belong to one shop
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id", nullable = false)
    private Shop shop;

    private String productName;
    private long quantity;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    @Enumerated(EnumType.STRING)
    private TransactionAction action;

    /*
      action can have three values:
      1. SALES
      2. STOCK_IN
      3. TRANSFER
     */

    // Getters and setters
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
}
