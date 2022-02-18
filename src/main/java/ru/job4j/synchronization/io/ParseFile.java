package ru.job4j.synchronization.io;

import java.io.*;
import java.util.function.Predicate;

final public class ParseFile implements Content {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    @Override
    public synchronized  String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(file.getAbsoluteFile().toString()))) {
            int data;
            while ((data = in.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
