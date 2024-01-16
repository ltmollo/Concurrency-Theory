package org.example;

import java.util.concurrent.Semaphore;

public class Fork {
    private final Semaphore semaphore = new Semaphore(1);

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release(){
        semaphore.release();
    }
}
