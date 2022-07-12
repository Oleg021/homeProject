package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HeadphoneServiceTest {

    private HeadphoneService target;
    private HeadphonesRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(HeadphonesRepository.class);
        target = new HeadphoneService(repository);
    }

    @Test
    void createAndSaveHeadphones_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  target.createAndSaveHeadphones(-1));
    }

    @Test
    void createAndSaveHeadphones_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  target.createAndSaveHeadphones(0));
    }

    @Test
    void createAndSaveHeadphones() {
        target.createAndSaveHeadphones(2);
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
    void saveHeadphone() {
        final Headphones headphones = new Headphones("Title", 100, 1000.0, "Model", Manufacturer.APPLE, HeadphonesType.WIRELESS);
        target.saveHeadphones(headphones);

        ArgumentCaptor<Headphones> argument = ArgumentCaptor.forClass(Headphones.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void saveLaptop_zeroCount() {
        final Headphones headphones = new Headphones("Title", 0, 1000.0, "Model", Manufacturer.APPLE, HeadphonesType.WIRELESS);
        target.saveHeadphones(headphones);

        ArgumentCaptor<Headphones> argument = ArgumentCaptor.forClass(Headphones.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }
}