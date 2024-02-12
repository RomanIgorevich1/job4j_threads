package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        pool.submit(() -> {
            System.out.printf("subject = Notification {%s} to email {%s}%n body = Add a new event to {%s}",
                    user.username(), user.email(), user.username());

        });
        pool.submit(() -> send(String.format("Notification {%s} to email {%s}", user.username(), user.email()),
                String.format("Add a new event to {%s}", user.username()), user.email()));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification email = new EmailNotification();
        email.emailTo(new User("Roman", "Roman@yandex.ru"));
        email.close();
    }
}