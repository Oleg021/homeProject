package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Headphones;
import com.nix.vyrvykhvost.model.Laptop;
import com.nix.vyrvykhvost.model.Phone;
import com.nix.vyrvykhvost.model.ProductType;

public class ProductFactory {
    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<Laptop> LAPTOP_SERVICE = LaptopService.getInstance();
    private static final ProductService<Headphones> HEADPHONES_SERVICE = HeadphoneService.getInstance();

    private ProductFactory() {
    }

    public static void createAndSave(ProductType type) {
        switch (type) {
            case PHONE -> PHONE_SERVICE.createAndSave(1);
            case LAPTOP -> LAPTOP_SERVICE.createAndSave(1);
            case HEADPHONES -> HEADPHONES_SERVICE.createAndSave(1);
            default -> throw new IllegalArgumentException("Unknown Product type: " + type);
        };
    }
}
