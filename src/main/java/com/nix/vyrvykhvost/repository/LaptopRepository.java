package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Laptop;

import java.util.*;

public class LaptopRepository implements CrudeRepositoryLaptop{
    private final List<Laptop> laptops;

    public LaptopRepository() {
        laptops = new LinkedList<>();
    }
    @Override
    public void save(Laptop laptop) {
        laptops.add(laptop);
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

    private static class LaptopCopy {
        private static void copy(final Laptop from, final Laptop to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
