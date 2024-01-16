package org.example;

import java.util.Random;

class Consumer extends Thread {
    private final Buffer2 _buf;
    private final int nbOfOperations;
    private final Random random = new Random();

    Consumer(Buffer2 buffer, int nbOfOperations){
        this._buf = buffer;
        this.nbOfOperations = nbOfOperations;
    }

    public void run() {
        for (int i = 0; i < nbOfOperations; ++i) {
            _buf.get();
            try{
                Thread.sleep(random.nextInt(900));
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
