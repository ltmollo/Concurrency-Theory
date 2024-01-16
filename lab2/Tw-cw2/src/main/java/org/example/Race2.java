package org.example;

class Race2 {
    public static void main(String[] args) {
        Counter cnt = new Counter(0);
        IThread it = new IThread(cnt);
        DThread dt = new DThread(cnt);

        it.start();
        dt.start();

        try {
            it.join();
            dt.join();
        } catch(InterruptedException ie) { }

        System.out.println("value=" + cnt.value());
    }
}