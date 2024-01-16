package org.zad1;

public class Reader extends Thread{
    private final ILibrary library;
    private final int iterations;

    public Reader(ILibrary library, int iterations){
        super();
        this.library = library;
        this.iterations = iterations;
    }

    @Override
    public void run(){
        for (int i = 0; i < iterations; i++){
            try {
                library.read();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
