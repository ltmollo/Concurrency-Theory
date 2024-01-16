package org.example;

public class GetRequest implements IMethodRequest {
    private final Servant servant;
    private final int elements;
    private final Future future;
    private final long requestingThreadID;

    public GetRequest(Future future, Servant servant, int elements) {
        this.servant = servant;
        this.future = future;
        this.elements = elements;
        this.requestingThreadID = Thread.currentThread().getId();
    }

    @Override
    public void call() {
        System.out.println(this);
        future.set(servant.get(elements));
    }

    @Override
    public boolean guard() {
        return servant.size() >= elements;
    }

    @Override
    public String toString() {
        return "[GetRequest] Thread " +
                requestingThreadID +
                " - " +
                elements +
                " items";
    }
}