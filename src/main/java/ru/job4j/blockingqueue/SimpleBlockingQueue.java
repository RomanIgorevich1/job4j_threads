package ru.job4j.blockingqueue;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("queue")
    private Queue<T> queue = new LinkedList<>();
    int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public Queue<T> getQueue() {
        return queue;
    }

    public void offer(T value) {
        synchronized (queue) {
            try {
                while (queue.size() == size) {
                    System.out.println("Сейчас работает поток " + Thread.currentThread().getName() + " Очередь заполнена");
                    queue.wait();
                }
                queue.offer(value);
                queue.notifyAll();
                System.out.println("Есть свободные места " + queue);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public T poll() {
        synchronized (queue) {
            try {
                while (queue.isEmpty()) {
                    System.out.println("Сейчас работает поток " + Thread.currentThread().getName() + " Нет данных ждем");
                    queue.wait();
                }
                queue.notifyAll();
                System.out.println("Забираем данные остаток: " + queue);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}
