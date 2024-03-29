package org.example;

import java.util.List;

public class AddRequest implements IMethodRequest {
    private final Servant servant;
    private final List<Integer> object;
    private final Future future;
    private final long requestingThreadID;

    public AddRequest(Future future, Servant servant, List<Integer> object) {
        this.future = future;
        this.servant = servant;
        this.object = object;
        this.requestingThreadID = Thread.currentThread().getId();
    }

    @Override
    public void call() {
        System.out.println(this);
        servant.put(object);
        future.set(List.of(object.size()));
    }

    @Override
    public boolean guard() {
        return servant.size() + object.size() <= servant.getCapacity();
    }

    @Override
    public String toString() {
        return "[AddRequest] Thread " +
                requestingThreadID +
                " - " +
                object.size() +
                " items";
    }
}