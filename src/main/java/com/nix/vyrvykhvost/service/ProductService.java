package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.model.ProductType;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ProductService<T extends Product> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
            logger.info("Product {} has been saved", product.getId());
        }
        repository.saveAll(products);
    }

    public T findById(String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void update(Product product) {
        repository.update((T) product);
    }
    public void delete(String id) {
        repository.delete(id);
    }

    public List<T> findAll() {
        return repository.findAll();
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

    public void printMoreThenFixedPrice(double price) {
        repository.findAll().stream()
                .filter(product -> product.getPrice() > price)
                .forEach(product -> System.out.println(product));
    }

    public int getSumOfProduct() {
        return repository.findAll().stream()
                .map(Product::getCount)
                .reduce(0, Integer::sum);
    }

    public Map<String, ProductType> sortByTitleAndConverToMap() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Product::getTitle))
                .distinct()
                .collect(Collectors.toMap(Product::getId, Product::getType, (a1, a2) -> a2));
    }

    public DoubleSummaryStatistics getPriceStatic() {
        return repository.findAll().stream().mapToDouble(Product::getPrice).summaryStatistics();
    }


}
