package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        pool.submit(() -> {
            System.out.printf("subject = Notification {%s} to email {%s}%n body = Add a new event to {%s}",
                    user.username(), user.email(), user.username());

        });
        pool.submit(() -> send(String.format("Notification {%s} to email {%s}", user.username(), user.email()),
                String.format("Add a new event to {%s}", user.username()), user.email()));
        close();
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification email = new EmailNotification();
        email.emailTo(new User("Roman", "Roman@yandex.ru"));
    }
}
