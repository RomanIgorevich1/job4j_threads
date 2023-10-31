package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i <= 10; i++) {
                    System.out.print("\rLoading : " + i + "%");
                    Thread.sleep(1000);
                }
                System.out.print("\nЗагрузка завершена");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

        });
        thread.start();

    }
}
