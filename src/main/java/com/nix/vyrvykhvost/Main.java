package com.nix.vyrvykhvost;

import com.nix.vyrvykhvost.context.ApplicationContext;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.PhoneService;
import com.nix.vyrvykhvost.util.parsers.Json;
import com.nix.vyrvykhvost.util.parsers.Xml;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static final PhoneService PHONE_SERVICE = new PhoneService(new PhoneRepository());
    private static final LaptopService LAPTOP_SERVICE = new LaptopService(new LaptopRepository());
    private static final HeadphoneService HEADPHONE_SERVICE = new HeadphoneService(new HeadphonesRepository());



    public static void main(String[] args) {

        /*LAPTOP_SERVICE.createAndSave(1);
        final String id = LAPTOP_SERVICE.getAll().get(0).getId();
        final LaptopType type = LAPTOP_SERVICE.getAll().get(0).getType();
        final String model = LAPTOP_SERVICE.getAll().get(0).getModel();
        //ifPresent
        LAPTOP_SERVICE.getPriceIfPresent(id);
        //orElse
        LAPTOP_SERVICE.printOrCreateGaming(id);
        LOG.info(" ");
        //orElseGet
        LAPTOP_SERVICE.printDefaultOrCreateGaming(id);
        //map
        LOG.info(" ");
        LAPTOP_SERVICE.printType(id);
        //ifPresentOrElse
        LAPTOP_SERVICE.printLaptopWithPrice(id, 25.0);
        //filter
        LAPTOP_SERVICE.filterGamingLaptop(id);
        //orElseThrow
        LAPTOP_SERVICE.printWorkerLaptopOrThrowException(id);
        //or
        LAPTOP_SERVICE.printLaptopWithModel(id, "model");*/


        /*LAPTOP_SERVICE.createAndSave(3);
        List<Laptop> laptops = new ArrayList<>();
        laptops.add(LAPTOP_SERVICE.getAll().get(0));
        laptops.add(LAPTOP_SERVICE.getAll().get(1));
        laptops.add(LAPTOP_SERVICE.getAll().get(2));
        System.out.println(laptops);
        ProductContainer<Laptop> laptopProductContainer = new ProductContainer<>(laptops.get(1));
        System.out.println(laptops.get(1).getPrice());
        Laptop laptop = laptopProductContainer.calculate();
        System.out.println(laptop.getPrice())*/;

        parserTest();
        applicationContextTest();
    }

    private static void applicationContextTest() {
        ApplicationContext context = ApplicationContext.getInstance();
        context.setCache();
        System.out.println(context.getCache());

    }

    private static void parserTest() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStreamJSON = loader.getResourceAsStream("phone.json");
        InputStream inputStreamXML = loader.getResourceAsStream("phone.xml");
        System.out.println("JSON: " + PHONE_SERVICE.phoneFromMap
                (Json.linesToMap(
                        Json.toLines(inputStreamJSON))));
        System.out.println();

        System.out.println("XML: " + PHONE_SERVICE.phoneFromMap(
                Xml.linesToMap(
                        Xml.toLines(inputStreamXML))));
    }

}
