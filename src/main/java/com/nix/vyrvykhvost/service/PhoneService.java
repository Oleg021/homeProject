package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Phone;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PhoneService extends ProductService<Phone> {
    private final PhoneRepository repository;

    public PhoneService(final PhoneRepository repository) {
        super(repository);
        this.repository = repository;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    @Override
    protected Phone creatProduct() {
        return new Phone(
                Phone.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer()
        );
    }
}
