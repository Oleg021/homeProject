package com.nix.vyrvykhvost.robots.robot;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
public class RobotTwo implements Runnable{

    private static AtomicInteger totalPoints = new AtomicInteger(0);

    private static final Random RANDOM = new Random();
    private CountDownLatch cdl;

    public RobotTwo(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            while (totalPoints.get() < 100){
                final int pointsInTick = RANDOM.nextInt(10,20);
                System.out.println("I am working. Points add - " + pointsInTick + " |total points -" + totalPoints.updateAndGet(x -> x+= pointsInTick));
                addCdl(pointsInTick);
                sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    private void addCdl(int points){
        for (int i = 0; i < points; i++){
            cdl.countDown();
        }
    }
}
