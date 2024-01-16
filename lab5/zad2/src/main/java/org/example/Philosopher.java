package org.example;

public class Philosopher extends Thread{
    private static final int EATING_TIME = 15;

    private static final int THINKING_TIME = 30;
    private static final int ITERATIONS = 50;

    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;

    long starvingTime = 0;

    Philosopher(int id, Fork leftFork, Fork rightFork){
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() throws InterruptedException {
        //System.out.println("Philosopher " + id + " started thinking");
        sleep(THINKING_TIME);
        //System.out.println("Philosopher " + id + " stopped thinking");
    }

    private void eat() throws InterruptedException {
        long start = System.currentTimeMillis();
        boolean full = false;

        while (!full) {
            if (leftFork.tryAcquire()) {
                if (rightFork.tryAcquire()){
                    starvingTime += System.currentTimeMillis() - start;
                    sleep(EATING_TIME);
                    full = true;
                    rightFork.release();
                }
                leftFork.release();
            }
        }
    }


    public void run() {
        for (int i = 0; i < ITERATIONS; i++) {
            try {
                think();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                eat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public long getStarvingTime(){
        return this.starvingTime;
    }
}

