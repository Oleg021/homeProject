package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.headphone.HeadphonesType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HeadphonesRepositoryTest {

    private HeadphonesRepository target;

    private Headphones headphones;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new HeadphonesRepository();
        headphones = new Headphones(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextDouble(1000.0),
                "Model-" + random.nextInt(10),
                Manufacturer.APPLE, HeadphonesType.WIRELESS
        );
    }

    @Test
    void save() {
        target.save(headphones);
        final List<Headphones> headphones = target.getAll();
        assertEquals(1, headphones.size());
        assertEquals(headphones.get(0).getId(), this.headphones.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Headphones> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void saveAll_singleLaptop() {
        target.saveAll(Collections.singletonList(headphones));
        final List<Headphones> headphones = target.getAll();
        assertEquals(1, headphones.size());
        assertEquals(headphones.get(0).getId(), this.headphones.getId());
    }

    @Test
    void saveAll_noHeadphones() {
        target.saveAll(Collections.emptyList());
        final List<Headphones> headphones = target.getAll();
        Assertions.assertEquals(0, headphones.size());
    }

    @Test
    void saveAll_manyHeadphones() {
        final Headphones otherHeadphones = new Headphones("Title", 500, 1000.0, "Model", Manufacturer.APPLE, HeadphonesType.WIRELESS);
        target.saveAll(List.of(headphones, otherHeadphones));
        final List<Headphones> headphones = target.getAll();
        assertEquals(2, headphones.size());
        assertEquals(headphones.get(0).getId(), this.headphones.getId());
        assertEquals(headphones.get(1).getId(), otherHeadphones.getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        final List<Headphones> headphonesList = new ArrayList<>();
        headphonesList.add(headphones);
        headphonesList.add(headphones);
        assertThrows(IllegalArgumentException.class, () ->target.saveAll(headphonesList));
    }

    @Test
    void saveAll_hasNull() {
        final List<Headphones> headphonesList = new ArrayList<>();
        headphonesList.add(headphones);
        headphonesList.add(null);
        assertThrows(IllegalArgumentException.class, () -> target.saveAll(headphonesList));
        final List<Headphones> actualResult = target.getAll();
        assertEquals(1, actualResult.size());
    }

    @Test
    void update() {
        final String newTitle = "New title";
        target.save(headphones);
        headphones.setTitle(newTitle);

        final boolean result = target.update(headphones);

        assertTrue(result);
        final List<Headphones> actualResult = target.getAll();
        assertEquals(1, actualResult.size());
        assertEquals(newTitle, actualResult.get(0).getTitle());
        assertEquals(headphones.getId(), actualResult.get(0).getId());
        assertEquals(headphones.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void update_noHeadphones() {
        target.save(headphones);
        final Headphones noHeadphones = new Headphones("Title", 500, 1000.0, "Model", Manufacturer.APPLE, HeadphonesType.WIRELESS);
        final boolean result = target.update(noHeadphones);

        assertFalse(result);
        final List<Headphones> actualResult = target.getAll();
        assertEquals(1, actualResult.size());
        assertEquals(headphones.getId(), actualResult.get(0).getId());
        assertEquals(headphones.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void delete() {
        target.save(headphones);
        final boolean result = target.delete(headphones.getId());
        assertTrue(result);
        final List<Headphones> actualResult = target.getAll();
        assertEquals(0, actualResult.size());
    }

    @Test
    void delete_noHeadphones() {
        target.save(headphones);
        final Headphones noHeadphones = new Headphones("Title", 500, 1000.0, "Model", Manufacturer.APPLE, HeadphonesType.WIRELESS);
        final boolean result = target.delete(noHeadphones.getId());
        assertFalse(result);
        final List<Headphones> actualResult = target.getAll();
        assertEquals(1, actualResult.size());
    }

    @Test
    void getAll() {
        target.save(headphones);
        final List<Headphones> actualResult = target.getAll();
        assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noHeadphones() {
        final List<Headphones> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void findById() {
        target.save(headphones);
        final Optional<Headphones> optionalHeadphones = target.findById(headphones.getId());
        assertTrue(optionalHeadphones.isPresent());
        final Headphones actualHeadphones = optionalHeadphones.get();
        assertEquals(headphones.getId(),actualHeadphones.getId());
    }
}