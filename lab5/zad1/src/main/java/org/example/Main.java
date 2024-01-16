package org.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
class Main {
    public static Integer PHILOSOPHERS = 5;

    public static void main(String[] args) {
        long[] times = testCaseWrapper();
        StringBuilder stringBuilder = new StringBuilder();
        for (long t : times) {
            stringBuilder.append(t);
            stringBuilder.append(",");
        }

        System.out.println(stringBuilder.toString());

        Path out = Paths.get("philosophers_deadlock.csv");

        try {
            Files.writeString(out, stringBuilder.toString(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long[] testCaseWrapper() {
        LinkedList<long[]> times = new LinkedList<long[]>();

        IntStream.range(0, 50).forEach(i -> {
            Optional<long[]> results = testCase();
            results.ifPresent(times::add);
        });

        return Arrays.stream(
                        times
                                .stream()
                                .reduce(new long[PHILOSOPHERS], (sums, r) -> {
                                    IntStream.range(0, PHILOSOPHERS).forEach(i -> {
                                        sums[i] += r[i];
                                    });
                                    return sums;
                                }))
                .map(v -> v / times.size())
                .toArray();
    }

    public static Optional<long[]> testCase() {
        var philosophers = new Philosopher[PHILOSOPHERS];
        var forks = new Fork[philosophers.length];

        IntStream.range(0, PHILOSOPHERS).forEach(i -> {
            forks[i] = new Fork();
        });

        IntStream.range(0, PHILOSOPHERS).forEach(i -> {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % forks.length]);
        });

        var executor = Executors.newFixedThreadPool(5);
        Arrays.stream(philosophers).forEach(executor::submit);
        executor.shutdown();
        boolean finishedNormally = false;
        try {
            finishedNormally = executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }

        if (finishedNormally) {
            long[] results = new long[PHILOSOPHERS];
            IntStream.range(0, PHILOSOPHERS).forEach(i -> {
                results[i] = philosophers[i].getStarvingTime();
            });

            return Optional.of(results);
        }

        System.out.println("timeout");
        executor.shutdownNow();
        return Optional.empty();
    }
}