package ru.job4j.cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        var rsl = memory.computeIfPresent(model.getId(), (key, val) -> {
            if (model.getVersion() != val.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base tmp = new Base(model.getId(), model.getVersion() + 1);
            tmp.setName(model.getName());
            return tmp;
        });
        return rsl != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }
}
