package com.nix.vyrvykhvost;

import com.nix.vyrvykhvost.model.Headphones;
import com.nix.vyrvykhvost.model.Laptop;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Phone;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.PhoneService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static final PhoneService PHONE_SERVICE = new PhoneService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final HeadphoneService HEADPHONE_SERVICE = new HeadphoneService();

    public static void main(String[] args) {

        PHONE_SERVICE.createAndSavePhones(2);
        LAPTOP_SERVICE.createAndSaveLaptops(2);
        HEADPHONE_SERVICE.createAndSaveHeadphones(2);

        PHONE_SERVICE.printAll();
        LAPTOP_SERVICE.printAll();
        HEADPHONE_SERVICE.printAll();
        LOG.info(" ");
        Phone phone1 = new Phone("A", 1,2, "21", Manufacturer.APPLE);
        Phone phone2 = new Phone("B", 11,22, "211", Manufacturer.APPLE);
        PhoneRepository phoneRepository = new PhoneRepository();
        LOG.info(phone1.toString());
        LOG.info(phone2.toString());
        LOG.info(phoneRepository.update(phone1));
        LOG.info(phone1.toString());
        LOG.info(phone2.toString());
        LOG.info(phoneRepository.delete(phone1.getId()));
        LOG.info(phone1.toString());
        LOG.info(phone2.toString());

    }
}
