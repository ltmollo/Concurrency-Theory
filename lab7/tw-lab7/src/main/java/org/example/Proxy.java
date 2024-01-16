package org.example;

import java.util.List;

public class Proxy {
    private final Servant servant;
    private final Scheduler scheduler = new Scheduler();

    public Proxy(int bufferSize) {
        this.servant = new Servant(bufferSize);

        scheduler.setDaemon(true);
        scheduler.start();
    }

    public Future put(List<Integer> object) {
        var future = new Future();
        scheduler.insert(new AddRequest(future, servant, object));
        return future;
    }

    public Future get(int numberOfElements) {
        var future = new Future();
        scheduler.insert(new GetRequest(future, servant, numberOfElements));
        return future;
    }
}