package org.example;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Test {
    public static void runTests(int nbOfProducers, int nbOfConsumers){
        System.out.println("Nb of Producers: " + nbOfProducers);
        System.out.println("Nb of Consumers: " + nbOfConsumers);
        System.out.println("------------------------------------");
        int lcm = Calculator.getInstance().lcm(nbOfProducers, nbOfConsumers);
        int nbOfProducts = lcm * 10;
        int nbOfProducerOperation = nbOfProducts / nbOfProducers;
        int nbOfConsumerOperation = nbOfProducts / nbOfConsumers;
        System.out.println("Each producer will produce: " + nbOfProducerOperation + " products");
        System.out.println("Each consumer will consume: " + nbOfConsumerOperation + " products");

        Buffer2 buffer = new Buffer2();
        ArrayList<Thread> threadArrayList = new ArrayList<>();

        IntStream.range(0, nbOfProducers).forEach(i -> threadArrayList.add(new Producer(buffer, nbOfProducerOperation)));
        IntStream.range(0, nbOfConsumers).forEach(i -> threadArrayList.add(new Consumer(buffer, nbOfConsumerOperation)));

        threadArrayList.forEach(Thread::start);
        threadArrayList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("All producers and consumers have finished");
        if(buffer.isEmpty()) System.out.println("All products have been consumed");
        else System.out.println("Not all products have been consumed");
        System.out.println("------------------------------------");
        System.out.println("------------------------------------");
    }
}
