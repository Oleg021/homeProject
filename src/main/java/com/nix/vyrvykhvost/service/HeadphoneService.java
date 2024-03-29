package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Singleton
public class HeadphoneService extends ProductService<Headphones> {
    private final HeadphonesRepository repository;
    private static HeadphoneService instance;

    public static HeadphoneService getInstance() {
        if (instance == null) {
            instance = new HeadphoneService(HeadphonesRepository.getInstance());
        }
        return instance;
    }

    @Autowired
    public HeadphoneService(HeadphonesRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    protected Headphones creatProduct() {
       return new Headphones(
                "Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer(),
                getRandomType()
        );
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private HeadphonesType getRandomType() {
        final HeadphonesType[] values = HeadphonesType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

}
