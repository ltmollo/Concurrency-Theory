package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class CExecutor implements IExecutor {
    private final ExecutorService executorService;
    private final List<Thread> workers = new LinkedList<>();

    public CExecutor(int threadNum) {
        executorService = Executors.newFixedThreadPool(threadNum);
    }

    @Override
    public void submit(Thread t) {
        workers.add(t);
    }

    @Override
    public void shutdownNow() {
        executorService.shutdownNow();
    }

    @Override
    public void awaitTermination() {
        try {
            workers.forEach(executorService::submit);
        } catch (RejectedExecutionException ignored){

        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
    }
}
