package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Headphones;
import com.nix.vyrvykhvost.model.Laptop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

public class HeadphonesRepository implements CrudeRepository<Headphones> {
    private static final Logger LOGGER = LogManager.getLogger(HeadphonesRepository.class);
    private final List<Headphones> headphones;
    private static HeadphonesRepository instance;

    public static HeadphonesRepository getInstance() {
        if (instance == null) {
            instance = new HeadphonesRepository();
        }
        return instance;
    }

    public HeadphonesRepository() {
        headphones = new LinkedList<>();
    }

    @Override
    public void save(Headphones headphone) {
        if (headphone == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null phone");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            checkDuplicates(headphone);
            headphones.add(headphone);
        }
    }

    private void checkDuplicates(Headphones headphone) {
        for (Headphones p : headphones) {
            if (headphone.hashCode() == p.hashCode() && headphone.equals(p)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate phone: " +
                        headphone.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }

    @Override
    public void saveAll(List<Headphones> headphones) {
        for (Headphones headphone : headphones) {
            save(headphone);
        }
    }

    @Override
    public boolean update(Headphones headphone) {
        final Optional<Headphones> result =findById(headphone.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Headphones originHeadpone = result.get();
        HeadphonesCopy.copy(headphone, originHeadpone);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Headphones> iterator = headphones.iterator();
        while (iterator.hasNext()) {
            final Headphones headphone = iterator.next();
            if (headphone.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Headphones> getAll() {
        if (headphones.isEmpty()) {
            return Collections.emptyList();
        }
        return headphones;
    }

    @Override
    public Optional<Headphones> findById(String id) {
        Headphones result = null;
        for (Headphones headphone : headphones) {
            if (headphone.getId().equals(id)) {
                result = headphone;
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Headphones> findAll() {
        if (headphones.isEmpty()) {
            return Collections.emptyList();
        }
        return headphones;
    }

    private static class HeadphonesCopy {
        private static void copy(final Headphones from, final Headphones to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
