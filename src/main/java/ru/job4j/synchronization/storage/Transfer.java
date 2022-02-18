package ru.job4j.synchronization.storage;

public interface Transfer {
    boolean transfer(int fromId, int toId, int amount);
}
