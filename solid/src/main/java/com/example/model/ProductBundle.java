package com.example.model;

import lombok.Setter;

@Setter
public class ProductBundle extends Product implements IProduct {
    protected int amount;

    @Override
    public String generateAddressForNotification() {
        throw new UnsupportedOperationException("Bundle can't be notified");
    }

    @Override
    public String getBasicInfo() {
        return "ProductBundle{" +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amountInBundle=" + amount +
                '}';
    }

    @Override
    public int getAmountInBundle() {
        return amount;
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amountInBundle=" + amount +
                '}';
    }
}
