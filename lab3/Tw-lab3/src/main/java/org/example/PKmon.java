package org.example;

import static org.example.Test.runTests;

public class PKmon {
    public static void main(String[] args) {
        runTests(1, 1);     // a. n1 = n2 = 1
        runTests(5, 4);     // b. n1 > n2
        runTests(3, 5);     // b. n1 < n2
    }
}