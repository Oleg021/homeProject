package com.nix.vyrvykhvost.robots.robot;

import lombok.SneakyThrows;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class RobotFive implements Runnable {

    private static final Random RANDOM = new Random();
    private int points;
    private AtomicInteger totalFuel;
    private final BlockingQueue<Integer> fuelQueue;

    private CountDownLatch waitLatch;
    public RobotFive(BlockingQueue<Integer> fuelQueue, CountDownLatch waitLatch) {
        this.fuelQueue = fuelQueue;
        this.waitLatch = waitLatch;
        totalFuel = new AtomicInteger(0);
    }

    private synchronized void fillBarrel(int fuel){
        totalFuel.updateAndGet(x -> x += fuel);
        notifyAll();
    }

    @SneakyThrows
    @Override
    public void run() {
        fillFuel();
        waitLatch.await();
        while (points < 100) {
            final int fuel = RANDOM.nextInt(350, 750);
            if(fuel <=totalFuel.get()){
                points += 10;
                System.out.println("I am start working. Total fuel after job = " + totalFuel.updateAndGet(x -> x -= fuel) + ". I need = " + fuel + " . Points = " + points);
                sleep(1000);
            } else {
                synchronized (this) {
                    System.out.println("I am waiting for fuel. Total = " + totalFuel + " . I need = " + fuel + ". Points = " + points);
                    this.wait();
                }
            }
        }
    }

    public void fillFuel(){
        Thread barrelFiller = new Thread(() -> {
            while (true) {
                try {
                    fillBarrel(fuelQueue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Total fuel: " + totalFuel.get());
            }
        }, "barrel-filler-thread");
        barrelFiller.setDaemon(true);
        barrelFiller.start();
    }
}
