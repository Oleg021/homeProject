package com.nix.vyrvykhvost.robots;

import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        new Thread(new Factory()).start();
    }
}
