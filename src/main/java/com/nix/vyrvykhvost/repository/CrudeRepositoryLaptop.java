package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Laptop;

import java.util.List;
import java.util.Optional;

public interface CrudeRepositoryLaptop {
    void save(Laptop laptop);

    void saveAll(List<Laptop> laptops);

    boolean update(Laptop laptop);

    boolean delete(String id);

    List<Laptop> getAll();

    Optional<Laptop> findById(String id);
}
