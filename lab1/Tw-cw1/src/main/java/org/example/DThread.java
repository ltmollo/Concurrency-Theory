package org.example;

public class DThread extends Thread{
    private Counter cnt;

    DThread(Counter cnt){
        this.cnt = cnt;
    }

    public void run(){
        for (int i = 0; i < 10000; i++) {
            this.cnt.dec();
        }
    }
}
