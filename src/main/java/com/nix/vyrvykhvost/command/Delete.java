package com.nix.vyrvykhvost.command;

import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.PhoneService;
import com.nix.vyrvykhvost.service.ProductService;
import com.nix.vyrvykhvost.util.UserInputUtil;
import com.nix.vyrvykhvost.util.Utils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Delete implements ICommand {
    private static final Logger LOG = LogManager.getLogger(Delete.class);
    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<Laptop> LAPTOP_SERVICE = LaptopService.getInstance();
    private static final ProductService<Headphones> HEADPHONES_SERVICE = HeadphoneService.getInstance();

    @Override
    public void execute() {
        LOG.info("What do you want to remove: ");
        ProductType[] types = ProductType.values();
        final List<String> names = Utils.getNames(types);
        int productTypeIndex = UserInputUtil.getUserInput(types.length, names);
        switch (types[productTypeIndex]) {
            case PHONE -> delete(PHONE_SERVICE);
            case LAPTOP-> delete(LAPTOP_SERVICE);
            case HEADPHONES -> delete(HEADPHONES_SERVICE);
            default -> throw new IllegalStateException("Unknown ProductType " + types[productTypeIndex]);
        }
    }

    private void delete(ProductService<? extends Product> service) {
        while (true) {
            LOG.info("Enter ID product: ");
            try {
                String id = SCANNER.nextLine();
                while (id.length() == 0) id = SCANNER.nextLine();
                service.findById(id);
                service.delete(id);
                return;
            } catch (IllegalArgumentException e) {
                LOG.info("Wrong ID. Try again");
            }
        }
    }
}
