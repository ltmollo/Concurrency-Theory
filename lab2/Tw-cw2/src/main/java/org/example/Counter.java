package org.example;

class Counter {
    private int _val;
    private final BinarySemaphore binarySemaphore = new BinarySemaphore();
    public Counter(int n) {
        _val = n;
    }
    public void inc() {
        binarySemaphore.P();
        _val++;
        binarySemaphore.V();
    }
    public void dec() {
        binarySemaphore.P();
        _val--;
        binarySemaphore.V();
    }
    public int value() {
        return _val;
    }
}
