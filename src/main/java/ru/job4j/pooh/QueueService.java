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
        if ("POST".equals(type)) {
            queues.putIfAbsent(nameQueue, new ConcurrentLinkedQueue<>());
            queues.get(nameQueue).offer(param);
            if (param != null) {
                rsl = new Resp(param, "201 Created");
            }
        } else if ("GET".equals(type)) {
            String info = queues.get(nameQueue).poll();
            if (info != null) {
                rsl = new Resp(info, "200 OK");
            }
        }
        return rsl;
    }
}