package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Product;

import java.util.List;
import java.util.Optional;

public interface CrudeRepository<T extends Product> {
    void save(T product);

    void saveAll(List<T> products);

    boolean update(T product);

    boolean delete(String id);

    List<T> getAll();

    Optional<T> findById(String id);

    List<T> findAll();
}
