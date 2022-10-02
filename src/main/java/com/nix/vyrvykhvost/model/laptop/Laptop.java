package com.nix.vyrvykhvost.model.laptop;

import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.model.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Laptop extends Product {
    @Column
    private  String model;
    @Transient
    private  Manufacturer manufacturer;
    @Column
   private  LaptopType laptopType;

    public Laptop(String title, int count, double price, String model, Manufacturer manufacturer, LaptopType laptopType) {
        super(title, count, price, ProductType.LAPTOP);
        this.model = model;
        this.manufacturer = manufacturer;
        this.laptopType = laptopType;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "manufacturer=" + manufacturer +
                ", id= " + id + '\'' +
                ", title= " + title + '\'' +
                ", count= " + count +
                ", price= " + price +
                ", type= " + laptopType +
                ", model= " + model +
                '}';
    }
}
