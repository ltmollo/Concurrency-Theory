package org.example;

public interface IExecutor {
    void submit(Thread t);

    void shutdownNow();

    void awaitTermination();
}