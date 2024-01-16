package org.example;

import java.util.Random;

class Producer extends Thread {
    private final Buffer2 _buf;
    private final int nbOfOperations;
    private final Random random = new Random();

    Producer(Buffer2 buffer, int nbOfOperations){
        this._buf = buffer;
        this.nbOfOperations = nbOfOperations;
    }

    public void run() {
        for (int i = 0; i < nbOfOperations; ++i) {
            _buf.put(i);
            try{
                Thread.sleep(random.nextInt(700));
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
