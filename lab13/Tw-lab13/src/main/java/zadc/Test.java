package zadc;

import java.util.ArrayList;
import java.util.stream.IntStream;
public class Test {
    public static void runTests(int nbOfProducers, int nbOfConsumers) {


        int nbOfProducts = 10000;
        int nbOfProducerOperation = nbOfProducts /
                nbOfProducers;
        int nbOfConsumerOperation = nbOfProducts /
                nbOfConsumers;

        Buffer buffer = new Buffer();
        var start = System.currentTimeMillis();

        ArrayList<Thread> threadArrayList = new ArrayList<>();
        IntStream.range(0, nbOfProducers).forEach(i ->
                threadArrayList.add(new Producer(buffer,
                        nbOfProducerOperation)));
        IntStream.range(0, nbOfConsumers).forEach(i ->
                threadArrayList.add(new Consumer(buffer,
                        nbOfConsumerOperation)));
        threadArrayList.forEach(Thread::start);
        threadArrayList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}