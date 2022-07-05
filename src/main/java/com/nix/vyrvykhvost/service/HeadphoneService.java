package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.Main;
import com.nix.vyrvykhvost.model.Headphones;
import com.nix.vyrvykhvost.model.HeadphonesType;
import com.nix.vyrvykhvost.model.LaptopType;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HeadphoneService {
    private static final Logger LOG = LogManager.getLogger(HeadphoneService.class);

    private static final Random RANDOM = new Random();
    private static final HeadphonesRepository REPOSITORY = new HeadphonesRepository();

    public void createAndSaveHeadphones(int count) {
        List<Headphones> headphones = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            headphones.add(new Headphones(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer(),
                    getRandomType()
            ));
        }
        REPOSITORY.saveAll(headphones);
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

    public void printAll() {
        for (Headphones headphone : REPOSITORY.getAll()) {
            LOG.info(headphone);
        }
    }
}
