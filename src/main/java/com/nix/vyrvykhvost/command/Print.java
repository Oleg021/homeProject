package com.nix.vyrvykhvost.command;

import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.model.ProductType;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.PhoneService;
import com.nix.vyrvykhvost.service.ProductService;
import com.nix.vyrvykhvost.util.UserInputUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Print implements ICommand {
    private static final Logger LOG = LogManager.getLogger(Print.class);
    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<com.nix.vyrvykhvost.model.laptop.Laptop> LAPTOP_SERVICE = LaptopService.getInstance();
    private static final ProductService<Headphones> HEADPHONES_SERVICE = HeadphoneService.getInstance();

    @Override
    public void execute() {
        LOG.info("What do you want to print:");
        final ProductType[] values = ProductType.values();
        final List<String> names = Arrays.stream(values)
                .map(Enum::name)
                .collect(Collectors.toList());
        final int userInput = UserInputUtil.getUserInput(values.length, names);

        switch (values[userInput]) {
            case PHONE -> PHONE_SERVICE.printAll();
            case LAPTOP -> LAPTOP_SERVICE.printAll();
            case HEADPHONES -> HEADPHONES_SERVICE.printAll();
            default -> {
                throw new IllegalArgumentException("Unknown ProductType " + values[userInput]);
            }
        }
    }
}
