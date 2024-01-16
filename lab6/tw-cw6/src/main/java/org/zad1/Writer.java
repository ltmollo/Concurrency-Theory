package org.zad1;

class Writer extends Thread {
    private final ILibrary library;
    private final int iterations;

    public Writer(ILibrary library, int iterations) {
        super();
        this.library = library;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                library.write();
            } catch (InterruptedException exception) {
                break;
            }
        }
    }
}