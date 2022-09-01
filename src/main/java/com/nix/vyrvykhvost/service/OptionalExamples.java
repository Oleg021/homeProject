package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.model.Manufacturer;

import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.repository.PhoneRepository;

import java.util.Optional;

public class OptionalExamples {
    private final PhoneRepository repository;

    public OptionalExamples(PhoneRepository repository) {
        this.repository = repository;
    }

    public void printIfPresent(String id) {
        final Optional<? extends Product> phoneOptional = repository.findById(id);
        phoneOptional.ifPresent(phone -> {
            System.out.println(phone);
        });
    }

    public void printOrGetDefault(String id) {
        final Product phone1 = repository.findById(id)
                .orElse(createAndSavePhone());
        System.out.println(phone1);

        System.out.println("~".repeat(5));

        final Product phone2 = repository.findById("123")
                .orElse(createAndSavePhone());
        System.out.println(phone2);
    }

    public void printOrCreatDefault(String id) {
        final Product phone1 = repository.findById(id)
                .orElseGet(() -> createAndSavePhone());
        System.out.println(phone1);

        System.out.println("~".repeat(5));

        final Product phone2 = repository.findById("123")
                .orElseGet(() -> {
                    System.out.println("TODO");
                    return createAndSavePhone();
                });
        System.out.println(phone2);
    }

    public void mapPhoneToString(String id) {
        final String phone1 = repository.findById(id)
                .map(p -> p.toString())
                .orElse("Phone not found");
        System.out.println(phone1);

        System.out.println("~".repeat(5));

        final String phone2 = repository.findById("112")
                .map(p -> p.toString())
                .orElse("Phone not found");
        System.out.println(phone2);
    }

    public void printOrPrintDefault(String id) {
        repository.findById(id).ifPresentOrElse(
                phone -> {
                    System.out.println(phone);
                },
                () -> {
                    System.out.println(createAndSavePhone());
                }
        );

        System.out.println("~".repeat(5));

        repository.findById("112").ifPresentOrElse(
                phone -> {
                    System.out.println(phone);
                },
                () -> {
                    System.out.println(createAndSavePhone());
                }
        );
    }

    public void checksPhoneLessThen(String id, int count) {
        repository.findById(id)
                .filter(phone -> phone.getCount() <= count)
                .ifPresentOrElse(
                        phone -> {
                            System.out.println(phone);
                        },
                        () -> {
                            System.out.println("Phone with count " + count + " not found");
                        }
                );
    }

    public void printPhoneOrElseThrowException(String id) {
        final Product phone1 = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Phone with id " + id + " not found"));
        System.out.println(phone1);

        System.out.println("~".repeat(5));

        final Product phone2 = repository.findById("123")
                .orElseThrow(() -> new IllegalArgumentException("Phone with id " + id + " not found"));
        System.out.println(phone2);
    }

    public void printPhone(String id) {
        repository.findById(id).or(() -> Optional.of(createAndSavePhone()))
                .ifPresent(phone -> System.out.println(phone));

        System.out.println("~".repeat(5));

        repository.findById("123").or(() -> Optional.of(createAndSavePhone()))
                .ifPresent(phone -> System.out.println(phone));
    }

    private Phone createAndSavePhone() {
        return new Phone("Title", 0, 0.0, "Model", Manufacturer.APPLE);
    }
}
