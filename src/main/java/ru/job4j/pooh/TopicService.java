package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * В режиме "topic" для каждого потребителя своя будет уникальная очередь с данными,
 * в отличие от режима "queue", где для все потребители получают данные из одной и той же очереди.
 * @author Sharon Alina
 * @version 1.0
 */
public class TopicService implements Service {
    /**
     * Хранение топиков осуществляется в карте topics. Ключ String внешней карты - название топика.
     * Ключ String внутренней карты - имя подписчика.
     */
    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp rsl = new Resp("", "204 No Content");
        String type = req.httpRequestType();
        String nameTopic = req.getSourceName();
        String param = req.getParam();
        /**
         *Получатель посылает запрос на получение данных с указанием топика.
         * Если топик отсутствует, то создается новый.
         * А если топик присутствует, то сообщение забирается из начала индивидуальной
         * очереди получателя и удаляется.
         * Когда получатель впервые получает данные из топика – для него создается
         * индивидуальная пустая очередь.
         * Все последующие сообщения от отправителей с данными для этого топика
         * помещаются в эту очередь тоже.
         */
        if ("GET".equals(type)) {
            topics.putIfAbsent(nameTopic, new ConcurrentHashMap<>());
            topics.get(nameTopic).putIfAbsent(param, new ConcurrentLinkedQueue<>());
            String textForResp = topics.get(nameTopic).get(param).poll();
            if (textForResp != null) {
                rsl =  new Resp(textForResp, "200 OK");
            }
        }
        /**
         * Отправитель посылает запрос на добавление данных с указанием топика (weather)
         * и значением параметра (temperature=18).
         * Сообщение помещается в конец каждой индивидуальной очереди получателей.
         * Если топика нет в сервисе, то данные игнорируются.
         */
        if ("POST".equals(type)) {
            var topic = topics.get(nameTopic);
            if (topic != null) {
                for (ConcurrentLinkedQueue<String> texts : topic.values()) {
                    texts.offer(param);
                }
                rsl = new Resp(param, "201 Created");
            }
        }
        return rsl;
    }
}
