package org.zad1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Main {
    public static void main(String[] args) {
        Mandelbrot mandelbrot = new Mandelbrot(600);
        ExecutorService executor = Executors.newFixedThreadPool(6);
        mandelbrot.run(executor);
        mandelbrot.display();
    }
}