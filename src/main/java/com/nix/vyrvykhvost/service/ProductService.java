package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class ProductService<T extends Product> {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    protected static final Random RANDOM = new Random();
    private final CrudeRepository<T> repository;

    protected ProductService(CrudeRepository<T> repository) {
        this.repository = repository;
    }

    public void createAndSave(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been bigger then 0");
        }
        List<T> products = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T product = creatProduct();
            products.add(product);
            LOG.info("Product {} has been saved", product.getId());
        }
        repository.saveAll(products);
    }

    protected abstract T creatProduct();

    public void save(T product) {
        if (product.getCount() == 0) {
            product.setCount(-1);
        }
        repository.save(product);
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        repository.getAll().stream()
                .sorted(Comparator.comparing(Product::getTitle))
                .forEach(System.out::println);
    }
}
