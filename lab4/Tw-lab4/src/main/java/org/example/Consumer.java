package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


class Consumer extends Thread {
    private final IBuffer buffer;
    private final int limit;
    private final int iterations;
    private final IExecutor executor;
    private final Random random = new Random();
    public Consumer(IExecutor executor, IBuffer buffer, int iterations) {
        this.iterations = iterations;
        this.buffer = buffer;
        limit = buffer.maxSize() / 2;
        this.executor = executor;
    }

    public void run() {
        for (int i = 0; i < iterations; i++) {
            var howMany = Math.max(1, random.nextInt(limit)-1);
            List<Integer> results = new LinkedList<>();
            try {
                buffer.get(results, howMany);
            } catch (InterruptedException exception) {
                break;
            }
        }

        buffer.unregisterConsumer();
        if (!buffer.isAnySideInterested()) {
            executor.shutdownNow();
        }
    }
}
