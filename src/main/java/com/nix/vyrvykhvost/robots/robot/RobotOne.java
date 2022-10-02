package com.nix.vyrvykhvost.robots.robot;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class RobotOne implements Runnable {

    private static final Random RANDOM = new Random();

    private final BlockingQueue<Integer> fuelQueue;

    public RobotOne(BlockingQueue<Integer> fuelQueue) {
        this.fuelQueue = fuelQueue;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            final int fuel = RANDOM.nextInt(500) + 500;
            System.out.println("I'm exporting " + fuel + " fuel");
            fuelQueue.add(fuel);
            sleep(3000);
        }
    }
}
