package org.example;

public class Counter {
    private int _val;
    private volatile boolean flag = false;
    public Counter(int n) {
        _val = n;
    }
    public void inc() {
        while(flag) {}
        _val++;
        flag = true;
    }
    public void dec() {
        while (!flag){}
        _val--;
        flag = false;
    }
    public int value() {
        return _val;
    }
}
