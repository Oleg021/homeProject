package com.nix.vyrvykhvost.command;

import com.nix.vyrvykhvost.model.ProductType;
import com.nix.vyrvykhvost.service.ProductFactory;
import com.nix.vyrvykhvost.util.UserInputUtil;
import com.nix.vyrvykhvost.util.Utils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.List;

public class Create implements ICommand {
    private static final Logger LOG = LogManager.getLogger(Create.class);
    @Override
    public void execute() {
        LOG.info("What do you want to create:");
        final ProductType[] values = ProductType.values();
        final List<String> names = Utils.getNames(values);
        final int userInput = UserInputUtil.getUserInput(values.length, names);
        ProductFactory.createAndSave(values[userInput]);
    }
}
