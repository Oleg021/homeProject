package com.nix.vyrvykhvost;

import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.PhoneService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static final PhoneService PHONE_SERVICE = new PhoneService(new PhoneRepository());
    private static final LaptopService LAPTOP_SERVICE = new LaptopService(new LaptopRepository());
    private static final HeadphoneService HEADPHONE_SERVICE = new HeadphoneService(new HeadphonesRepository());
    private static final OptionalExamples OPTIONAL_EXAMPLES = new OptionalExamples(new PhoneRepository());

    public static void main(String[] args) {

        LAPTOP_SERVICE.createAndSaveLaptops(1);
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
        LAPTOP_SERVICE.printLaptopWithModel(id, "model");
    }
}
