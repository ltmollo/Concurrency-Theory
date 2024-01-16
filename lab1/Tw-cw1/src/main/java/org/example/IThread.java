package org.example;

public class IThread extends Thread{

    private Counter cnt;

    IThread(Counter cnt){
        this.cnt = cnt;
    }

    public void run(){
        for(int i = 0; i < 10000; i++){
            this.cnt.inc();
        }
    }
}
