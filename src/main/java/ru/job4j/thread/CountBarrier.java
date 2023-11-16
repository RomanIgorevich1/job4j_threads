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
            while (count <= 5) {
                System.out.println("Сейчас работает поток " + Thread.currentThread().getName());
                count++;
            }
            System.out.printf("Поток %s будет пробуждать нити, Count равен: %s%n", Thread.currentThread().getName(), count);
            notifyAll();
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void awaiting() {
        synchronized (monitor) {
            while (count < 5) {
                try {
                    System.out.println("Ждем, count равен " + count + " задействован поток " + Thread.currentThread().getName());
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.printf("Поток %s  начал работу; TOTAL равен %s; COUNT равен %s%n",
                    Thread.currentThread().getName(), total--, count--);
            notifyAll();
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread first = new Thread(() -> {
            System.out.println("Начал работу поток " + Thread.currentThread().getName());
            while (barrier.total != 0) {
                barrier.awaiting();
            }
        });
        Thread second = new Thread(() -> {
            System.out.println("Начал работу поток " + Thread.currentThread().getName());
            while (barrier.total != 0) {
                barrier.count();
            }
        });
        first.start();
        second.start();
    }
}
