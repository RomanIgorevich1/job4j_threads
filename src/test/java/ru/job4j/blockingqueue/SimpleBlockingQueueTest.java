package ru.job4j.blockingqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import static org.assertj.core.api.Assertions.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenAddValue() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
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
            while (!blockingQueue.isEmpty() || !Thread.currentThread().isInterrupted()) {
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}