package org.example;

public class Race {
    public static void main(String[] args) {
        Histogram hist = new Histogram();

        for (int i = 0; i < 100; i++) {
            Counter cnt = new Counter(0);
            IThread increaseThread = new IThread(cnt);
            DThread decreaseThread = new DThread(cnt);

            increaseThread.start();
            decreaseThread.start();

            try {
                increaseThread.join();
                decreaseThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hist.addToHistogram(cnt);
        }
        hist.display();
    }
}
