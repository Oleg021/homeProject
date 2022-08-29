package com.nix.vyrvykhvost.model.laptop;

import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Laptop extends Product {
   /* private final String model;
    private final Manufacturer manufacturer;
   private final LaptopType laptopType;


    public Laptop(String title, int count, double price, String model, Manufacturer manufacturer, LaptopType laptopType) {
        super(title, count, price, ProductType.LAPTOP);
        this.model = model;
        this.manufacturer = manufacturer;
        this.laptopType = laptopType;
    }*/

    private LaptopType laptopType;
    private Manufacturer manufacturer;
    private String model;

    public Laptop() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setLaptopType(LaptopType laptopType) {
        this.laptopType = laptopType;
    }

    public LaptopType getLaptopType() {
        return laptopType;
    }

    public static class Builder {
        private Laptop laptop;

        public Builder(long price, LaptopType laptopType, Manufacturer manufacturer, String model) {
            if (laptopType == null) {
                throw new IllegalArgumentException("LaptopType can not be null");
            }
            laptop = new Laptop();
            laptop.setPrice(price);
            laptop.setLaptopType(laptopType);
            laptop.setManufacturer(manufacturer);
        }

        public Builder setTittle(String title){
            if (title.length() > 20) {
                throw new IllegalArgumentException("Title cant be more then 20 symbols");
            }
            laptop.title = title;
            return this;
        }

        public Builder setCount(int count){
            if (count < 0) {
                throw new IllegalArgumentException("Count cant be less then 0");
            }
            laptop.count = count;
            return this;
        }

        public Builder setPrice(long price){
            laptop.price = price;
            return this;
        }

        public Laptop build(){
            return laptop;
        }

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
