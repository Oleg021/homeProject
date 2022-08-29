package com.example.controller;

import com.example.model.Product;
import com.example.service.NotificationService;
import com.example.service.ProductFactory;
import com.example.service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {

    private static final ProductService PRODUCT_SERVICE = ProductService.getInstance();
    private static final NotificationService NOTIFICATION_SERVICE = NotificationService.getInstance();
    private static final ProductFactory PRODUCT_FACTORY = ProductFactory.getInstance();

    @Override
    public void run() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(PRODUCT_FACTORY.generateRandomProduct());
        }
        list.forEach(PRODUCT_SERVICE::save);
        for (Object elem : PRODUCT_SERVICE.getAll()) {
            System.out.println(elem);
        }
        System.out.println("notifications sent: " + NOTIFICATION_SERVICE.filterNotifiableProductsAndSendNotifications());
    }

}
