package com.nix.vyrvykhvost.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Invoice {
    private String id;
    private double sum;
    private List<Product> products;
    private LocalDateTime time;
    private List<String> productIds;
}