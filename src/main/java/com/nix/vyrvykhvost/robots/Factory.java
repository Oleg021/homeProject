package com.nix.vyrvykhvost.robots;

import com.nix.vyrvykhvost.robots.robot.RobotFive;
import com.nix.vyrvykhvost.robots.robot.RobotFour;
import com.nix.vyrvykhvost.robots.robot.RobotOne;
import com.nix.vyrvykhvost.robots.robot.RobotTwo;
import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class Factory implements Runnable{

    @SneakyThrows
    @Override
    public void run() {
        CountDownLatch RobotsTwoThreeLatch = new CountDownLatch(100);
        CountDownLatch RobotsFourLatch = new CountDownLatch(1);
        BlockingQueue<Integer> fuelQueue = new LinkedBlockingQueue<Integer>();
        Thread Robot1 = new Thread(new RobotOne(fuelQueue), "Robot-1");
        Robot1.setDaemon(true);
        Robot1.start();

        new Thread(new RobotTwo(RobotsTwoThreeLatch), "Robot-2").start();
        Thread.currentThread().sleep(100);
        new Thread(new RobotTwo(RobotsTwoThreeLatch), "Robot-3").start();
        new Thread(new RobotFour(RobotsTwoThreeLatch, RobotsFourLatch), "Robot-4").start();

        new Thread(new RobotFive(fuelQueue, RobotsFourLatch), "Robot-5").start();
    }
}

