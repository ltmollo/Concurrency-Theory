package org.example;

public class Test {
    public static void main(String[] args) {
        GeneralSemaphore semaphore = new GeneralSemaphore(3);

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.P();
                    System.out.println(Thread.currentThread().getName() + " uzyskał dostęp do zasobu.");
                    Thread.sleep((long) (Math.random() * 2000));
                    System.out.println(Thread.currentThread().getName() + " zwalnia dostęp do zasobu.");
                    semaphore.V();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
