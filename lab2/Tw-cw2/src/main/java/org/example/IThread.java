package org.example;

class IThread extends Thread {
    private Counter _cnt;
    public IThread(Counter c) {
        _cnt = c;
    }
    public void run() {
        for (int i = 0; i < 100000000; ++i) {
//		try { this.sleep(50); }
//			catch(Exception e) {}
            _cnt.inc();
        }
    }
}