package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.model.Laptop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

@Singleton
public class LaptopRepository implements CrudeRepository<Laptop> {
    private static final Logger LOGGER = LogManager.getLogger(LaptopRepository.class);
    private final List<Laptop> laptops;
    private static LaptopRepository instance;

    public static LaptopRepository getInstance() {
        if (instance == null) {
            instance = new LaptopRepository();
        }
        return instance;
    }

    @Autowired
    public LaptopRepository() {
        laptops = new LinkedList<>();
    }

    @Override
    public void save(Laptop laptop) {
        if (laptop == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null phone");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            checkDuplicates(laptop);
            laptops.add(laptop);
        }
    }

    private void checkDuplicates(Laptop laptop) {
        for (Laptop p : laptops) {
            if (laptop.hashCode() == p.hashCode() && laptop.equals(p)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate phone: " +
                        laptop.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }

    @Override
    public void saveAll(List<Laptop> laptops) {
        for (Laptop laptop : laptops) {
            save(laptop);
        }
    }

    @Override
    public boolean update(Laptop laptop) {
        final Optional<Laptop> result = findById(laptop.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Laptop originLaptop = result.get();
        LaptopCopy.copy(laptop, originLaptop);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Laptop> iterator = laptops.listIterator();
        while (iterator.hasNext()) {
            final Laptop laptop = iterator.next();
            if (laptop.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Laptop> getAll() {
        if (laptops.isEmpty()) {
            return Collections.emptyList();
        }
        return laptops;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Laptop result = null;
        for (Laptop laptop : laptops) {
            if (laptop.getId().equals(id)) {
                result = laptop;
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Laptop> findAll() {
        if (laptops.isEmpty()) {
            return Collections.emptyList();
        }
        return laptops;
    }

    private static class LaptopCopy {
        private static void copy(final Laptop from, final Laptop to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
