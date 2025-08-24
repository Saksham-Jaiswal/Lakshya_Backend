package com.backend.Lakshya.model;

import jakarta.persistence.*;

@Entity
@Table(name = "master")
public class Master {



    private String owner;
    @Id
    @Column(name = "shop_name",nullable=false,unique=true)
    private String shopName;
    private String salesPersonName;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }
}
