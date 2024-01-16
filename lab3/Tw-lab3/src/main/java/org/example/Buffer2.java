package org.example;

import java.util.LinkedList;

class Buffer2 {
    private final LinkedList<Integer> _buf = new LinkedList<>();
    private final GeneralSemaphore slotsAvailable;
    private final GeneralSemaphore notEmpty;
    private final BinarySemaphore mutex;
    private final int MAX_ITEMS_IN_BUFFER = 10;

    public Buffer2() {
        this.slotsAvailable = new GeneralSemaphore(MAX_ITEMS_IN_BUFFER);
        this.notEmpty = new GeneralSemaphore(0);
        this.mutex = new BinarySemaphore();
    }

    public boolean isEmpty(){
        return this._buf.isEmpty();
    }

    public void put(int i) {
        this.slotsAvailable.P();
        this.mutex.P();
        this._buf.add(i);
        this.mutex.V();
        // System.out.println("Producer put " + i);
        this.notEmpty.V();
    }

    public int get() {
        this.notEmpty.P();
        this.mutex.P();
        int v = this._buf.removeFirst();
        this.mutex.V();
        // System.out.println("Consumer took " + result);
        this.slotsAvailable.V();
        return v;
    }
}