package org.example;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.IntStream;

class Consumer2 extends Thread {
    private final List<String> results = new LinkedList<>();

    private final Buffer3 _buf;
    private final Random random = new Random();

    public Consumer2(Buffer3 buffer) {
        _buf = buffer;
    }

    public void run() {
        IntStream.range(0, _buf.getBufferSize()).forEach(i -> {
            try {
                Thread.sleep(random.nextInt(400));
            } catch (InterruptedException ignored) {
            }
            Object obj = _buf.get(i);
            results.add(obj.toString());
        });
    }

    public List<String> getResults() {
        return results;
    }
}
