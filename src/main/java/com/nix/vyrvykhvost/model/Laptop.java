package com.nix.vyrvykhvost.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Laptop extends Product {
    private final String model;
    private final Manufacturer manufacturer;
   private final LaptopType type;

    public Laptop(String title, int count, double price, String model, Manufacturer manufacturer, LaptopType type) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "manufacturer=" + manufacturer +
                ", id= " + id + '\'' +
                ", title= " + title + '\'' +
                ", count= " + count +
                ", price= " + price +
                ", type= " + type +
                ", model= " + model +
                '}';
    }
}
