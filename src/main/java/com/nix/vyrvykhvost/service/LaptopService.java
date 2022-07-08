package com.nix.vyrvykhvost.service;


import com.nix.vyrvykhvost.Main;
import com.nix.vyrvykhvost.model.Laptop;
import com.nix.vyrvykhvost.model.LaptopType;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Phone;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
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
        REPOSITORY.saveAll(laptops);
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
       for (Laptop laptop : REPOSITORY.getAll()) {
           LOG.info(laptop);
       }
    }
}
