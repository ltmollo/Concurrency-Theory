package org.example;

class BinarySemaphore {
    private boolean _stan = true;
    private int _czeka = 0;

    public BinarySemaphore() {
    }

    public synchronized void P()  {
        while (!_stan) {
            try {
                _czeka++;
                wait();
                _czeka--;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        _stan = false;
    }

    public synchronized void V() {
        _stan = true;
        if(_czeka > 0){
        notify();}
    }
}
