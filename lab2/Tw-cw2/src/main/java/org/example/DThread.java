package org.example;

class DThread extends Thread {
    private Counter _cnt;
    public DThread(Counter c) {
        _cnt = c;
    }
    public void run() {
        for (int i = 0; i < 100000000; ++i) {
            _cnt.dec();
//		try { this.sleep(1); }
//			catch(Exception e) {}
        }
    }
}