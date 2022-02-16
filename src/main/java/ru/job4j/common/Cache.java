package ru.job4j.common;

public class Cache {
    private volatile static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
