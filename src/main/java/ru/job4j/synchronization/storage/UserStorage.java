package ru.job4j.synchronization.storage;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Storage, Transfer {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.get(user.getId()).setAmount(user.getAmount());
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (users.containsKey(fromId) && users.containsKey(toId)
                && users.get(fromId).getAmount() >= amount) {
            User from = users.get(fromId);
            User to = users.get(toId);
            int amountFrom = from.getAmount() - amount;
            int amountTo = to.getAmount() + amount;
            from.setAmount(amountFrom);
            to.setAmount(amountTo);
            rsl = true;
        }
        return rsl;
    }
}
