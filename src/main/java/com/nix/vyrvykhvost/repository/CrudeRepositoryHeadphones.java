package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Headphones;

import java.util.List;
import java.util.Optional;

public interface CrudeRepositoryHeadphones {
    void save(Headphones headphone);

    void saveAll(List<Headphones> headphones);

    boolean update(Headphones headphone);

    boolean delete(String id);

    List<Headphones> getAll();

    Optional<Headphones> findById(String id);
}
