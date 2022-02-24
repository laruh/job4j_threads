package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queues =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String type = req.httpRequestType();
        String nameQueue = req.getSourceName();
        String param = req.getParam();
        queues.putIfAbsent(nameQueue, new ConcurrentLinkedQueue<>());
        ConcurrentLinkedQueue<String> queue = queues.get(nameQueue);
        if ("POST".equals(type)) {
            queue.offer(param);
            return param != null ? new Resp(param, "200") : new Resp("", "204");
        } else if ("GET".equals(type)) {
            String rsl = queue.poll();
            return rsl != null ? new Resp(rsl, "200") : new Resp("", "204");
        }
        return null;
    }
}