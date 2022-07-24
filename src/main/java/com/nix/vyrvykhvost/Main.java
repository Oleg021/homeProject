package com.nix.vyrvykhvost;

import com.nix.vyrvykhvost.model.*;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import com.nix.vyrvykhvost.service.HeadphoneService;
import com.nix.vyrvykhvost.service.LaptopService;
import com.nix.vyrvykhvost.service.OptionalExamples;
import com.nix.vyrvykhvost.service.PhoneService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static final PhoneService PHONE_SERVICE = new PhoneService(new PhoneRepository());
    private static final LaptopService LAPTOP_SERVICE = new LaptopService(new LaptopRepository());
    private static final HeadphoneService HEADPHONE_SERVICE = new HeadphoneService(new HeadphonesRepository());
    private static final OptionalExamples OPTIONAL_EXAMPLES = new OptionalExamples(new PhoneRepository());

    public static void main(String[] args) {

        /*PHONE_SERVICE.createAndSavePhones(2);
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
        LOG.info(phone2.toString());*/


        private static void optionalExamples() {
            PHONE_SERVICE.createAndSavePhones(1);
            final String id = PHONE_SERVICE.getAll().get(0).getId();
            OPTIONAL_EXAMPLES.printIfPresent(id);
            OPTIONAL_EXAMPLES.printOrGetDefault(id);
            OPTIONAL_EXAMPLES.printOrCreatDefault(id);
            OPTIONAL_EXAMPLES.mapPhoneToString(id);
            OPTIONAL_EXAMPLES.printOrPrintDefault(id);
            OPTIONAL_EXAMPLES.checksPhoneLessThen(id, 1000);
            OPTIONAL_EXAMPLES.checksPhoneLessThen(id, 10);
            OPTIONAL_EXAMPLES.checksPhoneLessThen("123", 1000);
            try {
                OPTIONAL_EXAMPLES.printPhoneOrElseThrowException(id);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            OPTIONAL_EXAMPLES.printPhone(id);
        }

    }
}
