package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pipe {
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> runCase());
    }

    public static void runCase() {
        int BUFFER_SIZE = 100;

        Function<Integer, Integer> addNine = i -> (i + 9);
        Function<Integer, Integer> multByThree = i -> (3 * i);
        Function<Integer, String> duplicateString = i -> (i.toString() + i.toString());
        Function<String, String> addPrefix = s -> ("prefix" + s);
        Function<String, String> addSufix = s -> (new StringBuilder(s).reverse().toString());

        List<Function> transforms = Arrays.asList(
                multByThree,
                addNine,
                duplicateString,
                addPrefix,
                addSufix
        );

        Buffer3 buffer = new Buffer3(BUFFER_SIZE, transforms.size());
        List<Thread> threadList = new LinkedList<>();
        threadList.add(new Producer2(buffer));
        IntStream.range(0, transforms.size()).forEach(i -> {
            threadList.add(new PipeThread(buffer, i, transforms.get(i)));
        });

        var consumer = new Consumer2(buffer);
        threadList.add(consumer);
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        List<String> calculatedResults = consumer.getResults();
        List<String> expectedResults = IntStream
                .range(0, BUFFER_SIZE)
                .mapToObj(i -> {
                    List<Object> tempList = new ArrayList<>(transforms.size());
                    tempList.add(i);
                    transforms.forEach(t ->{
                        Object o = tempList.get(tempList.size() - 1);
                        tempList.add(t.apply(o));
                    });
                    return tempList.get(tempList.size() - 1).toString();
                })
                .collect(Collectors.toList());

        Collections.sort(calculatedResults);
        Collections.sort(expectedResults);
        boolean res = calculatedResults.equals(expectedResults);
        if (res) System.out.println("Result Equals");
        else System.out.println("Result not Equals");
    }
}
