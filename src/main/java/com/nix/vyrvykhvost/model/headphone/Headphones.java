package com.nix.vyrvykhvost.model.headphone;

import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.model.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Headphones extends Product {
    private final String model;
    private final Manufacturer manufacturer;
    private final HeadphonesType headphonesType;

    public Headphones(String title, int count, double price, String model, Manufacturer manufacturer, HeadphonesType headphonesType) {
        super(title, count, price, ProductType.HEADPHONES);
        this.model = model;
        this.manufacturer = manufacturer;
        this.headphonesType = headphonesType;
    }



    @Override
    public String toString() {
        return "Headphones{" +
                "manufacturer=" + manufacturer +
                ", id= " + id + '\'' +
                ", title= " + title + '\'' +
                ", count= " + count +
                ", price= " + price +
                ", type= " + headphonesType +
                ", model= " + model +
                '}';
    }
}
