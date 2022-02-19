package ru.job4j.wait;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimpleBlockingQueueTest {

    @Test
    public void when2Consumer1Producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                queue.poll();
            }
        });
        Thread consumer1 = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                queue.poll();
            }
        });
        producer.start();
        consumer.start();
        consumer1.start();
        producer.join();
        consumer.join();
        consumer1.join();
        assertThat(queue.poll(), equalTo(8));
    }
}