package com.nix.vyrvykhvost.robots.robot;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class RobotFour implements Runnable{

    private static final Random RANDOM = new Random();
    private static AtomicInteger totalPoints = new AtomicInteger(0);
    private CountDownLatch cdl;
    private CountDownLatch setLatch;

    public RobotFour(CountDownLatch waitLatch, CountDownLatch setLatch) {
        this.cdl = waitLatch;
        this.setLatch = setLatch;
    }

    @SneakyThrows
    @Override
    public void run() {
        cdl.await();
        System.out.println("Start work");
        while(totalPoints.get() < 100){
            final int probability = RANDOM.nextInt(100);
            if(probability < 30){
                System.out.println("Robot broke microchip");
                totalPoints.set(0);
            } else {
                int points = RANDOM.nextInt(25, 35);
                totalPoints.updateAndGet(x -> x += points);
                System.out.println("points add - " + points + " total points - " + totalPoints.get());
                sleep(1000);
            }
        }
        setLatch.countDown();
    }
}
