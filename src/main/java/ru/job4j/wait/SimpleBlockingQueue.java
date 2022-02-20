package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int limit;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.size() == limit) {
            this.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        T rsl = queue.poll();
        this.notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
