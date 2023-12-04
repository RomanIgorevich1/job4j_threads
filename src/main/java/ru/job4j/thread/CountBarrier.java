package ru.job4j.thread;

public class CountBarrier {
    private final Object monitor = this;
    private int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void awaiting() {
        synchronized (monitor) {
            while (count != total) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread first = new Thread(() -> {
            System.out.println("Начал работу поток " + Thread.currentThread().getName());
                barrier.awaiting();

        });
        Thread second = new Thread(() -> {
            System.out.println("Начал работу поток " + Thread.currentThread().getName());
            while (barrier.count != barrier.total) {
                barrier.count();
            }
        });
        first.start();
        second.start();
    }
}
