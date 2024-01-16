package org.example;

public class GeneralSemaphore {
    private final BinarySemaphore mutex;        // dostęp do metod P i V
    private final BinarySemaphore resource;     // dostęp do permits
    private int permits;

    public GeneralSemaphore(int permits){
        if(permits < 0){
            throw new IllegalArgumentException("Permits must > 0.");
        }else{
            mutex = new BinarySemaphore();
            resource = new BinarySemaphore();
            this.permits = permits;
        }
    }

    public void P(){
        resource.P();
        mutex.P();
        permits--;
        if (this.permits > 0) {
            resource.V();
        }
        mutex.V();
    }

    public void V(){
        mutex.P();
        permits++;
        if(permits >= 1){
            resource.V();
        }
        mutex.V();
    }
}