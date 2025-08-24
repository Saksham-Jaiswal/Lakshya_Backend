package com.backend.Lakshya.model;

import jakarta.persistence.*;
// it holds the data about the shop
@Entity
@Table(
        name = "shop",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"shop_name"}),       // ensure shop names are unique
                @UniqueConstraint(columnNames = {"salesperson_id"})   // ensure one salesperson → one shop
        }
)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "shop_name", nullable = false, unique = true)
    private String shopName;

    // Many shops can belong to one owner
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "userId", nullable = false)
    private Users owner;

    // One salesman can only be linked to one shop
    @OneToOne
    @JoinColumn(name = "salesperson_id", referencedColumnName = "userId", unique = true, nullable = false)
    private Users salesperson;

    // Getters and setters
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public Users getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Users salesperson) {
        this.salesperson = salesperson;
    }
}
