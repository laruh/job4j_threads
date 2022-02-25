package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForPublisher1 = "temperature=42";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        String paramForSubscriber3 = "client3888";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1));
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber3));
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher));
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1));
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2));
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber3));
        Resp result4 = topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher1));
        Resp result5 = topicService.process(
                new Req("POST", "topic", "wind", paramForPublisher1));
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is(""));
        assertThat(result3.text(), is("temperature=18"));
        assertThat(result4.text(), is("temperature=42"));
        assertThat(result5.status(), is("204"));
    }
}