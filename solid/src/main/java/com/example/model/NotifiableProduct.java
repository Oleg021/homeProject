package com.example.model;

import lombok.Setter;

@Setter
public class NotifiableProduct extends Product implements IProduct{
    protected String channel;

    public String generateAddressForNotification() {
        return "somerandommail@gmail.com";
    }

    @Override
    public int getAmountInBundle() {
        throw new UnsupportedOperationException("Product is not a bundle");
    }

    @Override
    public String getBasicInfo() {
        return "NotifiableProduct{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public String toString() {
        return "NotifiableProduct{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

}