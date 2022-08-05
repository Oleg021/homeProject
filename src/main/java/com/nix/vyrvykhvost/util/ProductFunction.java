package com.nix.vyrvykhvost.util;

import com.nix.vyrvykhvost.model.Product;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ProductFunction implements Function<Map<String, Objects>, Product> {
    @Override
    public Product apply(Map<String, Objects> filds) {
        String title = filds.get("title").toString();
        Objects count = filds.get("count");
        Objects price = filds.get("price");
        Objects productType = filds.get("product type");
        return null;
    }

}
