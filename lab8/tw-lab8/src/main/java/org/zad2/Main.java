package org.zad2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Main {
    private static final List<Integer> maxItersList = List.of(100, 500, 1000, 5000);
    private static final List<Integer> threadCountList = List.of(2, 4, 8, 16);

    public static void saveResults(String fname, List<String> data) {
        Path out = Paths.get(fname + ".csv");
        try {
            Files.write(out, data, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newFixedThreadPoolTest() {
        LinkedList<String> results = new LinkedList<>();
        for (Integer t : threadCountList) {
            for (Integer i : maxItersList) {
                Mandelbrot mandelbrot = new Mandelbrot(i);
                ExecutorService executor = Executors.newFixedThreadPool(t);
                mandelbrot.run(executor);
                executor.shutdownNow();
                long time = mandelbrot.getTimeOfExecution();
                results.add(t + "," + i + "," + time);
            }
        }

        saveResults("newFixedThreadPool", results);
    }

    public static void newSingleThreadExecutorTest() {
        LinkedList<String> results = new LinkedList<>();
        for (Integer i : maxItersList) {
            Mandelbrot mandelbrot = new Mandelbrot(i);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            mandelbrot.run(executor);
            executor.shutdownNow();
            long time = mandelbrot.getTimeOfExecution();
            results.add(i + "," + time);
        }

        saveResults("newSingleThreadExecutor", results);
    }

    public static void newCachedThreadPoolTest() {
        LinkedList<String> results = new LinkedList<>();
        for (Integer i : maxItersList) {
            Mandelbrot mandelbrot = new Mandelbrot(i);
            ExecutorService executor = Executors.newCachedThreadPool();
            mandelbrot.run(executor);
            executor.shutdownNow();
            long time = mandelbrot.getTimeOfExecution();
            results.add(i + "," + time);
        }

        saveResults("newCachedThreadPool", results);
    }

    public static void newWorkStealingPoolTest() {
        LinkedList<String> results = new LinkedList<>();
        for (Integer t : threadCountList) {
            for (Integer i : maxItersList) {
                Mandelbrot mandelbrot = new Mandelbrot(i);
                ExecutorService executor = Executors.newWorkStealingPool(t);
                mandelbrot.run(executor);
                executor.shutdownNow();
                long time = mandelbrot.getTimeOfExecution();
                results.add(t + "," + i + "," + time);
            }
        }

        saveResults("newWorkStealingPool", results);
    }

    public static void main(String[] args) {
        newFixedThreadPoolTest();
        newSingleThreadExecutorTest();
        newCachedThreadPoolTest();
        newWorkStealingPoolTest();
    }
}