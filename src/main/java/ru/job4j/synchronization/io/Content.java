package ru.job4j.synchronization.io;

import java.util.function.Predicate;

public interface Content {
    String getContent(Predicate<Character> filter);
}
