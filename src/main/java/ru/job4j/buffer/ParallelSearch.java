package ru.job4j.buffer;

import ru.job4j.blockingqueue.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args)  {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(queue.poll());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        new Thread(() -> {
            try {
                for (int index = 0; index != 3; index++) {
                    queue.offer(index);
                    Thread.sleep(500);
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                Thread.currentThread().interrupt();
            }
            consumer.interrupt();
        }).start();
    }
}
