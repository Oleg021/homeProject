package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Phone;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PhoneService {
    private static final Logger LOG = LogManager.getLogger(PhoneService.class);

    private static final Random RANDOM = new Random();
    private static final PhoneRepository REPOSITORY = new PhoneRepository();

    public List<Phone> createAndSavePhones(int count) {
        List<Phone> phones = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            phones.add(new Phone(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            ));
        }
        REPOSITORY.saveAll(phones);
        return phones;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Phone phone : REPOSITORY.getAll()) {
            LOG.info(phone);
        }
    }
}
