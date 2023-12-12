package ru.job4j.blockingqueue;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("queue")
    private Queue<T> queue = new LinkedList<>();
    private int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == size) {
                queue.wait();
            }
            queue.offer(value);
            queue.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            queue.notifyAll();
        }
        return queue.poll();
    }
}