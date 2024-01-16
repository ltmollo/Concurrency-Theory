package org.zad1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class Main {

    public static void main(String[] args) {
        List<Integer> readersNums = List.of(10, 40, 70, 100);
        List<Integer> writersNums = List.of(1, 4, 7, 10);

        LinkedList<String> results = new LinkedList<String>();

        for (Integer w : writersNums) {
            for (Integer r : readersNums) {
                ILibrary lib = new SemaphoreLibrary();
                ILibrary lib2 = new LockLibrary();
                double avg1 = testCaseWrapper(r, w, lib);
                double avg2 = testCaseWrapper(r, w, lib2);
                results.add(w + "," + r + "," + round(avg1, 2) + "," + round(avg2, 2));
            }
        }

        Path out = Paths.get("results.csv");
        try {
            Files.write(out, results, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double round(double v, int places) {
        v = Math.round(v * Math.pow(10, places));
        v = v / Math.pow(10, places);
        return v;
    }

    private static double testCaseWrapper(int r, int w, ILibrary lib) {
        LinkedList<Long> times = new LinkedList<Long>();

        IntStream.range(0, 200).forEach(i -> {
            long before = System.currentTimeMillis();

            testCase(r, w, lib);

            long diff = System.currentTimeMillis() - before;
            times.add(diff);
        });

        return times.stream().mapToLong(i -> i).average().getAsDouble();
    }

    private static void testCase(int r, int w, ILibrary lib) {
        var executor = Executors.newFixedThreadPool(r + w);
        LinkedList<Thread> threadList = new LinkedList<Thread>();

        IntStream.range(0, r).forEach(i ->
                threadList.add(new Reader(lib, 200))
        );
        IntStream.range(0, w).forEach(i ->
                threadList.add(new Writer(lib, 100))
        );

        threadList.forEach(executor::submit);

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
    }
}