package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String type = req.httpRequestType();
        String nameTopic = req.getSourceName();
        String param = req.getParam();
        if ("GET".equals(type)) {
            topics.putIfAbsent(param, new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = topics.get(param);
            if (topic.get(nameTopic) == null) {
                topic.putIfAbsent(nameTopic, new ConcurrentLinkedQueue<>());
                return new Resp("", "200");
            } else {
                return new Resp(topic.get(nameTopic).poll(), "200");
            }
        }
        if ("POST".equals(type)) {
            boolean success = false;
            for (Map.Entry<String, ConcurrentHashMap<String,
                    ConcurrentLinkedQueue<String>>> entry : topics.entrySet()) {
                if (entry.getValue().containsKey(nameTopic)) {
                    entry.getValue().get(nameTopic).offer(param);
                    success = true;
                }
            }
            return success ? new Resp(param, "200") : new Resp("", "204");
        }
        return null;
    }
}
