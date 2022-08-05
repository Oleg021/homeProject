package com.nix.vyrvykhvost.util;

import com.nix.vyrvykhvost.model.Product;

import java.util.function.Predicate;

public class ProductPredicate implements Predicate<Product> {
    @Override
    public boolean test(Product product) {
        return product.getPrice() != 0.0;
    }
}
