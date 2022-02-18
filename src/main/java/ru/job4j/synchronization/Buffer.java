package ru.job4j.synchronization;

public class Buffer {
    private StringBuilder buffer = new StringBuilder();

    public void add(int value) {
        System.out.print(value);
        synchronized (this) {
            buffer.append(value);
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            return buffer.toString();
        }
    }
}
