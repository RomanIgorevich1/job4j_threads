package ru.job4j.blockingqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenAddValue() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                        blockingQueue.offer(i);
            }
        });
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!blockingQueue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
                list.add(blockingQueue.poll());
            }
        });
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(list).containsExactly(0, 1, 2, 3, 4);
    }
}