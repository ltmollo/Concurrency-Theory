package org.zad1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class SemaphoreLibrary implements ILibrary {
    private final Semaphore writerSemaphore = new Semaphore(1);
    private final Semaphore readerSemaphore = new Semaphore(1);
    private final AtomicInteger readerCount = new AtomicInteger(0);

    @Override
    public void read() throws InterruptedException {
        readerSemaphore.acquire();
        var readers = readerCount.incrementAndGet();
        if (readers == 1) {
            writerSemaphore.acquire();
        }
        readerSemaphore.release();
        // read
        readerSemaphore.acquire();
        readers  = readerCount.decrementAndGet();
        if (readers == 0) {
            writerSemaphore.release();
        }
        readerSemaphore.release();
    }

    @Override
    public void write() throws InterruptedException {
        writerSemaphore.acquire();
        // write
        writerSemaphore.release();
    }
}
