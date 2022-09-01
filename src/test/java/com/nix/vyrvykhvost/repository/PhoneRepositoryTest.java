package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.phone.Laptop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class PhoneRepositoryTest {

    private PhoneRepository target;

    private Laptop phone;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new PhoneRepository();
        phone = new Laptop(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextDouble(1000.0),
                "Model-" + random.nextInt(10),
                Manufacturer.APPLE
        );
    }

    @Test
    void save() {
        target.save(phone);
        final List<Laptop> phones = target.getAll();
        Assertions.assertEquals(1, phones.size());
        Assertions.assertEquals(phones.get(0).getId(), phone.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void saveAll_singlePhone() {
        target.saveAll(Collections.singletonList(phone));
        final List<Laptop> phones = target.getAll();
        Assertions.assertEquals(1, phones.size());
        Assertions.assertEquals(phones.get(0).getId(), phone.getId());
    }

    @Test
    void saveAll_noPhone() {
        target.saveAll(Collections.emptyList());
        final List<Laptop> phones = target.getAll();
        Assertions.assertEquals(0, phones.size());
    }

    @Test
    void saveAll_manyPhones() {
        final Laptop otherPhone = new Laptop("Title", 500, 1000.0, "Model", Manufacturer.APPLE);
        target.saveAll(List.of(phone, otherPhone));
        final List<Laptop> phones = target.getAll();
        Assertions.assertEquals(2, phones.size());
        Assertions.assertEquals(phones.get(0).getId(), phone.getId());
        Assertions.assertEquals(phones.get(1).getId(), otherPhone.getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        final List<Laptop> phones = new ArrayList<>();
        phones.add(phone);
        phones.add(phone);
        Assertions.assertThrows(IllegalArgumentException.class, () ->target.saveAll(phones));
    }

    @Test
    void saveAll_hasNull() {
        final List<Laptop> phones = new ArrayList<>();
        phones.add(phone);
        phones.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(phones));
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void update() {
        final String newTitle = "New title";
        target.save(phone);
        phone.setTitle(newTitle);

        final boolean result = target.update(phone);

        Assertions.assertTrue(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(newTitle, actualResult.get(0).getTitle());
        Assertions.assertEquals(phone.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(phone.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void update_noPhone() {
        target.save(phone);
        final Laptop noPhone = new Laptop("Title", 500, 1000.0, "Model", Manufacturer.APPLE);
        final boolean result = target.update(noPhone);

        Assertions.assertFalse(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(phone.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(phone.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void delete() {
        target.save(phone);
        final boolean result = target.delete(phone.getId());
        Assertions.assertTrue(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_noPhone() {
        target.save(phone);
        final Laptop noPhone = new Laptop("Title", 500, 1000.0, "Model", Manufacturer.APPLE);
        final boolean result = target.delete(noPhone.getId());
        Assertions.assertFalse(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll() {
        target.save(phone);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noPhones() {
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void findById() {
        target.save(phone);
        final Optional<Laptop> optionalPhone = target.findById(phone.getId());
        Assertions.assertTrue(optionalPhone.isPresent());
        final Laptop actualPhone = optionalPhone.get();
        Assertions.assertEquals(phone.getId(),actualPhone.getId());
    }
}