package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Phone;

import java.util.List;
import java.util.Optional;

public interface CrudRepositoryPhone {
    void save(Phone phone);

    void saveAll(List<Phone> phones);

    boolean update(Phone phone);

    boolean delete(String id);

    List<Phone> getAll();

    Optional<Phone> findById(String id);
}
