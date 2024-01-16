package org.example;

import java.util.Random;
import java.util.stream.IntStream;


class Producer extends Thread {
    private final IBuffer buffer;
    private final Random random = new Random();
    private final int limit;
    private final int iterations;
    private final IExecutor executor;

    public Producer(IExecutor executor, IBuffer buffer, int iterations) {
        this.iterations = iterations;
        this.buffer = buffer;
        this.limit = buffer.maxSize() / 2;
        this.executor = executor;
    }

    public void run() {
        for(int i = 0; i < iterations; i++) {
            var howMany = Math.max(1, random.nextInt(limit)-1);
            int[] data = new int[howMany];
            IntStream.range(0, howMany).forEach(j -> {
                data[j] = j;
            });

            try {
                buffer.put(data);
            } catch (InterruptedException exception) {
                break;
            }
        }
        buffer.unregisterProducer();
        if(!buffer.isAnySideInterested()){
            executor.shutdownNow();
        }
    }
}
