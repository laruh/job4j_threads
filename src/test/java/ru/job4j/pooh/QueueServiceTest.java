package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        String paramForPostMethod1 = "temperature=13";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod));
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod1));
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null));
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null));
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather", null));
        assertThat(result.text(), is("temperature=18"));
        assertThat(result1.text(), is("temperature=13"));
        assertThat(result2.status(), is("204"));
        assertThat(result2.text(), is(""));
    }
}