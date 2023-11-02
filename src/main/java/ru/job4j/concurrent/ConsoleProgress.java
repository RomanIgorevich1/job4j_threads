package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        int count = 1;
            try {
                Thread.sleep(500);
                while (!Thread.currentThread().isInterrupted()) {
                    if (count == process.length) {
                        count = 0;
                    }
                    System.out.print("\rLoading ... " + process[count]);
                    count++;
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3000);
        progress.interrupt();
    }
}
