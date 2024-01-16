package org.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;


public class PKMain {
    enum MODE {
        MONITORS,
        JAVA_CONCURRENT
    }

    public static void main(String[] args) {
        List<Integer> producers = List.of(3, 10, 20);
        List<Integer> consumers = List.of(3, 10, 20);
        List<Integer> bufferSizes = List.of(5, 10, 50, 100);

        LinkedList<String> results = new LinkedList<String>();

        for (Integer p : producers) {
            for (Integer c : consumers) {
                for (Integer bs : bufferSizes) {
                    double avgM = test(p, c, bs, MODE.MONITORS);
                    double avgJC = test(p, c, bs, MODE.JAVA_CONCURRENT);

                    System.out.println(p + "p/" + c + "c [" + bs + "]: " + avgM + "ms / " + avgJC + "ms");
                    results.add(p + ", " + c + ", " + bs + ", " + round(avgM, 2) + ", " + round(avgJC, 2));
                }
            }
        }

        Path out = Paths.get("results.csv");
        try {
            Files.write(out, results, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double round(double v, int places){
        v = Math.round(v * Math.pow(10, places));
        v = v / Math.pow(10, places);
        return v;
    }

    public static double test(int prodNum, int consNum, int halfBufSize, MODE mode) {
        var times = new LinkedList<Long>();

        IntStream.range(0, 200).forEach(i -> {
            long before = System.currentTimeMillis();

            if (mode == MODE.MONITORS) {
                testMonitors(prodNum, consNum, halfBufSize);
            } else {
                testJavaConcurrent(prodNum, consNum, halfBufSize);
            }

            long after = System.currentTimeMillis();
            long diff = after - before;
            times.add(diff);
        });

        return times.stream().mapToLong(i -> i).average().getAsDouble();
    }

    public static void testJavaConcurrent(int m, int n, int M) {
        var buffer = new CBuffer(M, m, n);
        var executor = new CExecutor(n+m);

        runTasks(m, n, buffer, executor);
    }

    public static void testMonitors(int m, int n, int M) {
        var buffer = new MBuffer(M, m, n);
        var executor = new MExecutor();

        runTasks(m, n, buffer, executor);
    }

    public static void runTasks(int m, int n, IBuffer buffer, IExecutor executor){
        IntStream.range(0, n).forEach(i ->
                executor.submit(
                        new Consumer(executor, buffer, 100)
                ));
        IntStream.range(0, m).forEach(i ->
                executor.submit(
                        new Producer(executor, buffer, 100)
                ));

        executor.awaitTermination();
    }
}
