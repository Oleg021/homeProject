package com.nix.vyrvykhvost.service;


import com.nix.vyrvykhvost.Main;
import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopService {
    private static final Logger LOG = LogManager.getLogger(LaptopService.class);
    private static final Random RANDOM = new Random();
    private static final LaptopRepository REPOSITORY = new LaptopRepository();
    private final LaptopRepository repository;

    public LaptopService(LaptopRepository repository) {
        this.repository = repository;
    }

    public void createAndSaveLaptops(int count) {
        List<Laptop> laptops = new LinkedList<>();
        if (count == 0 || count < 0) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null phone");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            for (int i = 0; i < count; i++) {
                laptops.add(new Laptop(
                        "Title-" + RANDOM.nextInt(1000),
                        RANDOM.nextInt(500),
                        RANDOM.nextDouble(100.0),
                        "Model-" + RANDOM.nextInt(10),
                        getRandomManufacturer(),
                        getRandomType()
                ));
            }
        }
        repository.saveAll(laptops);
    }

    public void saveLaptop(Laptop laptop) {
        if (laptop.getCount() == 0) {
            laptop.setCount(-1);
        }
        repository.save(laptop);
    }

    public List<Laptop> getAll() {
        return repository.getAll();
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private LaptopType getRandomType() {
        final LaptopType[] values = LaptopType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
       for (Laptop laptop : repository.getAll()) {
           LOG.info(laptop);
       }
    }

    public void getPriceIfPresent(String id) {
        final Optional<Laptop> laptopOptional = repository.findById(id);
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            laptopOptional.ifPresent(laptop -> {
                LOG.info(laptop.getPrice());
            });
        }
    }

    public void printOrCreateGaming(String id) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            LOG.info(repository.findById(id)
                    .orElse(new Laptop("Title", 0, 0.0, "model", Manufacturer.LENOVO, LaptopType.GAMING)));
            LOG.info("*".repeat(5));
            LOG.info(repository.findById("123").orElse(new Laptop("Title", 0, 0.0, "model", Manufacturer.LENOVO, LaptopType.GAMING)));
        }
    }

    public void printDefaultOrCreateGaming(String id) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            LOG.info(repository.findById(id).orElseGet(() -> createAndSaveLaptop()));
            LOG.info("*".repeat(5));
            LOG.info(repository.findById("123").orElseGet(() -> createAndSaveLaptop()));
        }

    }

    public void printType(String id) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            LOG.info(repository.findById(id).map(laptop -> laptop.getType()));
        }
    }

    public void printLaptopWithPrice(String id, double price) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            repository.findById(id)
                    .filter(laptop -> laptop.getPrice() == price)
                    .ifPresentOrElse(laptop -> {
                                LOG.info(laptop);
                            }
                            , () -> {
                                LOG.info("Laptop with price " + price + " not found");
                            });
        }
    }

    public void filterGamingLaptop(String id) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            LOG.info(repository.findById(id)
                    .filter(laptop -> laptop.getType().equals(LaptopType.GAMING)));
        }
    }

    public void printWorkerLaptopOrThrowException(String id) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            repository.findById(id)
                    .filter(laptop -> laptop.getType().equals(LaptopType.WORKER))
                    .orElseThrow(() -> new IllegalArgumentException("Laptop with type " + LaptopType.WORKER + " not found"));
        }
    }

    public void printLaptopWithModel(String id, String model) {
        if (id.equals(null)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Error in id");
            LOG.error(exception.getMessage(), exception);
            throw exception;
        } else {
            repository.findById(id)
                    .filter(laptop -> laptop.getModel().equals(model))
                    .or(() -> Optional.of(createAndSaveLaptop()))
                    .ifPresent(laptop -> LOG.info(laptop));
        }
    }

    private Laptop createAndSaveLaptop() {
        return new Laptop("title", 0, 0.0, "model", Manufacturer.LENOVO, LaptopType.GAMING);
    }
}
