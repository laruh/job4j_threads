package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queues =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp rsl = new Resp("", "204 No Content");
        String type = req.httpRequestType();
        String nameQueue = req.getSourceName();
        String param = req.getParam();
        queues.putIfAbsent(nameQueue, new ConcurrentLinkedQueue<>());
        ConcurrentLinkedQueue<String> queue = queues.get(nameQueue);
        if ("POST".equals(type)) {
            queue.offer(param);
            if (param != null) {
                rsl = new Resp(param, "201 Created");
            }
        } else if ("GET".equals(type)) {
            String info = queue.poll();
            if (info != null) {
                rsl = new Resp(info, "200 OK");
            }
        }
        return rsl;
    }
}