package com.nix.vyrvykhvost.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Product {
    protected String id;
    protected String title;
    protected int count;
    protected double price;
    protected ProductType productType;
    protected Invoice invoice;

    protected Product(String title, int count, double price, ProductType productType) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.count = count;
        this.price = price;
        this.productType = productType;
    }
}
