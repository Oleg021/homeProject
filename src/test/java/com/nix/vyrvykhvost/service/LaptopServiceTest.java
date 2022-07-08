package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Laptop;
import com.nix.vyrvykhvost.model.LaptopType;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class LaptopServiceTest {

    private LaptopService target;
    private LaptopRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(LaptopRepository.class);
        target = new LaptopService(repository);
    }

    @Test
    void createAndSaveLaptops_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  target.createAndSaveLaptops(-1));
    }

    @Test
    void createAndSaveLaptops_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  target.createAndSaveLaptops(0));
    }

    @Test
    void createAndSaveLaptops() {
        target.createAndSaveLaptops(2);
        Mockito.verify(repository).saveAll(Mockito.anyList());
    }

    @Test
    void getAll() {
        target.getAll();
        Mockito.verify(repository).getAll();
    }

    @Test
    void printAll() {
        target.printAll();
        Mockito.verify(repository).getAll();
    }

    @Test
    void saveLaptop() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", Manufacturer.APPLE, LaptopType.GAMING);
        target.saveLaptop(laptop);

        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void saveLaptop_zeroCount() {
        final Laptop laptop = new Laptop("Title", 0, 1000.0, "Model", Manufacturer.APPLE, LaptopType.GAMING);
        target.saveLaptop(laptop);

        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }
}