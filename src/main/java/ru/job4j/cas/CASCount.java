package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        int result;
        do {
            result = get();
            temp = result;
        } while (!count.compareAndSet(result, temp + 1));
    }

    public int get() {
        return count.get();
    }
}
