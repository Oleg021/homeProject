package com.nix.vyrvykhvost.container;

import com.nix.vyrvykhvost.model.Product;

import java.util.Comparator;

public class ComporatorProduct<T extends Product> implements Comparator<T> {
    @Override
    public int compare(T first, T second) {
        if (second.getPrice() == first.getPrice()) {
            if (first.getTitle().equals(second.getTitle())) {
                return Integer.compare(first.getCount(), second.getCount());
            }
            return first.getTitle().compareTo(second.getTitle());
        }
        return Double.compare(second.getPrice(), first.getPrice());
    }
}
