package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.phone.OperationSystem;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.repository.PhoneRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class PhoneService extends ProductService<Phone> {
    private final PhoneRepository repository;
    private static PhoneService instance;

    public static PhoneService getInstance() {
        if (instance == null) {
            instance = new PhoneService(PhoneRepository.getInstance());
        }
        return instance;
    }

    @Autowired
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

    public Phone phoneFromMap(Map<String, String> productMap) {
        Function<Map<String, String>, Phone> mapToProduct = (map) -> {
             return new Phone(map.getOrDefault("title", "N/A"),
                    Integer.parseInt(map.getOrDefault("count", String.valueOf(0))),
                    Double.parseDouble(map.getOrDefault("price", String.valueOf(0))),
                    map.getOrDefault("model", "N/A"),
                    Manufacturer.valueOf(map.getOrDefault("manufacturer", Manufacturer.SAMSUNG.name())),
                    LocalDateTime.parse(map.get("created"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")),
                    map.get("currency"),
                    new OperationSystem(map.get("operating-system.designation"), Integer.parseInt(map.get("operating-system.version"))));

        };
        return mapToProduct.apply(productMap);
    }
}
