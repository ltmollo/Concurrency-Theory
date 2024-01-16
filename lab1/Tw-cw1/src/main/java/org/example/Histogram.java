package org.example;

import java.util.HashMap;

public class Histogram {

    private final HashMap<Integer, Integer> histogram = new HashMap<>();

    public void addToHistogram(Counter cnt) {
        int variable = cnt.value();
        if (histogram.containsKey(variable)) {
            histogram.put(variable, histogram.get(variable) + 1);
        } else {
            histogram.put(variable, 1);
        }
    }

    public void display() {
        histogram.forEach((key, value) ->
                System.out.println("wartość=" + key + ", wystąpienia: " + value)
        );
    }
}
