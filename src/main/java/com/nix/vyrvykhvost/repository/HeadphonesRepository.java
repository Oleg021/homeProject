package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Headphones;

import java.util.*;

public class HeadphonesRepository implements CrudeRepositoryHeadphones{
    private final List<Headphones> headphones;

    public HeadphonesRepository() {
        headphones = new LinkedList<>();
    }

    @Override
    public void save(Headphones headphone) {
        headphones.add(headphone);
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

    private static class HeadphonesCopy {
        private static void copy(final Headphones from, final Headphones to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
