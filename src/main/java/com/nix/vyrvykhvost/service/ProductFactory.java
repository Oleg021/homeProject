package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.ProductType;
import com.nix.vyrvykhvost.model.phone.Phone;

public class ProductFactory {
    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<com.nix.vyrvykhvost.model.laptop.Laptop> LAPTOP_SERVICE = LaptopService.getInstance();
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
