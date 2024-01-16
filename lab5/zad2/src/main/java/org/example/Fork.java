package org.example;

import java.util.concurrent.Semaphore;

public class Fork {
    private final Semaphore semaphore = new Semaphore(1);

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void release(){
        semaphore.release();
    }
}
