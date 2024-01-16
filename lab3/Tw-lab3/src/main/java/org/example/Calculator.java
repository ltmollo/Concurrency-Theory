package org.example;

import java.util.Random;

public class Calculator {
    private static Calculator calculator = null;

    private Calculator(){};
    public static Calculator getInstance(){
        if (calculator == null){
            calculator = new Calculator();
        }
        return calculator;
    }
    public int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }
    public int gcd(int a, int b) {
        while (b > 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
