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
        int number = 5;
        Thread producer = new Thread(() -> {
            for (int i = 0; i < number; i++) {
                try {
                    blockingQueue.offer(i);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

        });
        producer.start();
        Thread consumer = new Thread(() -> {
            while (list.size() != number || !Thread.currentThread().isInterrupted()) {
                try {
                    list.add(blockingQueue.poll());
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(list).containsExactly(0, 1, 2, 3, 4);
    }
}