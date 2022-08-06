package com.nix.vyrvykhvost.command;

import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.PhoneService;
import com.nix.vyrvykhvost.service.ProductService;
import com.nix.vyrvykhvost.util.UserInputUtil;
import com.nix.vyrvykhvost.util.Utils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Update implements ICommand {
    private static final Logger LOG = LogManager.getLogger(Update.class);
    private static final ProductService PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService LAPTOP_SERVICE = LaptopService.getInstance();
    private static final ProductService HEADPHONES_SERVICE = HeadphoneService.getInstance();


    @Override
    public void execute() {
        LOG.info("What do you want to update:");
        ProductType[] types = ProductType.values();
        final List<String> names = getNamesOfType(types);
        int productTypeIndex = UserInputUtil.getUserInput(types.length, names);
        switch (types[productTypeIndex]) {
            case PHONE -> updateProduct(PHONE_SERVICE);
            case LAPTOP -> updateProduct(LAPTOP_SERVICE);
            case HEADPHONES -> updateProduct(HEADPHONES_SERVICE);
            default -> throw new IllegalStateException("Unknown ProductType " + types[productTypeIndex]);
        }
    }

    private List<String> getNamesOfType(final ProductType[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (ProductType type : values) {
            names.add(type.name());
        }
        return names;
    }

    private void updateProduct(ProductService<? extends Product> service) {
        while (true) {
            LOG.info("Enter product ID");
            try {
                String id = SCANNER.nextLine();
                while (id.length() == 0) id = SCANNER.nextLine();
                Product product = service.findById(id);
                final List<String> names = Arrays.asList("Update Title", "Update Price", "Update Count");
                int stop = 0;
                do {
                    int productTypeIndex = UserInputUtil.getUserInput(names.size(), names);
                    switch (names.get(productTypeIndex)) {
                        case "Update Title" -> updateTitle(product);
                        case "Update Price" -> updatePrice(product);
                        case "Update Count" -> updateCount(product);
                    }
                    LOG.info("Enter -1 to complete the update, or any other number to continue updating");
                    stop = SCANNER.nextInt();
                } while (stop != -1);
                service.update(product);
                return;
            } catch (IllegalArgumentException e) {
                LOG.info("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updatePrice(Product product) throws IOException {
        System.out.println("Enter new Price");
        String price = SCANNER.nextLine();
        if (Utils.isNumeric(price)) {
            product.setPrice(Long.parseLong(price));
        } else {
            System.out.println("Wrong input");
            updatePrice(product);
        }
    }

    private void updateTitle(Product product) throws IOException {
        System.out.println("Enter new Title");
        String title = SCANNER.nextLine();
        product.setTitle(title);
    }

    private void updateCount(Product product) throws IOException {
        System.out.println("Enter new Count");
        String count = SCANNER.nextLine();
        if (Utils.isNumeric(count)) {
            product.setCount(Integer.parseInt(count));
        } else {
            System.out.println("Wrong input");
            updateCount(product);
        }
    }
}
