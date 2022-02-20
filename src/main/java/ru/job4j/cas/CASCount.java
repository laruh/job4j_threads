package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer newCount;
        Integer refCount;
        do {
            refCount = count.get();
            newCount = refCount + 1;
        } while (!count.compareAndSet(refCount, newCount));
    }

    public int get() {
        return count.get();
    }
}