package ru.job4j.synchronization.storage;

public interface Storage {
    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);
}
