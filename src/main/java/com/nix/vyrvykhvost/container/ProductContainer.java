package com.nix.vyrvykhvost.container;

import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.service.LaptopService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Random;

public class ProductContainer<T extends Product> {
    private static final Logger LOG = LogManager.getLogger(ProductContainer.class);
    private T product;

    public ProductContainer(T product) {
        this.product = product;
    }

    public T getInfo() {
        return product;
    }

    public T calculate() {
        double percent = new Random().nextInt(10,30);
        double price = product.getPrice();
        double newPrice = price - ((price * percent) / 100);
        product.setPrice(newPrice);
        return product;
    }

    public <E extends Number> T increaseCount (E number){
        if (number == null){
            throw new IllegalArgumentException("number == null");
        }
        int intNumber = (int) number;
        int count = product.getCount();
        int newCount = count + intNumber;
        product.setCount(newCount);
        return product;
    }
}
