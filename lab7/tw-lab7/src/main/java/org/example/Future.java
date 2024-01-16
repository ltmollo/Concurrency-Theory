package org.example;

import java.util.List;

public class Future {
    private List<Integer> object;
    private boolean completed = false;


    public void set(List<Integer> object) {
        this.object = object;
        complete();
    }

    public synchronized void complete(){
        completed = true;
        notify();
    }

    public boolean isCompleted(){
        return completed;
    }

    public synchronized void await(){
        while(!isCompleted()){
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }
}