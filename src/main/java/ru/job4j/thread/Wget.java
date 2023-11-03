package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import java.util.concurrent.TimeUnit;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        File file = new File("tmp.xml");
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long timeStart = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                long finish = (System.nanoTime() - timeStart);
                System.out.println("Read 1024 bytes : "  + finish + " nano");
                long millis = TimeUnit.NANOSECONDS.toMillis((speed * 1000000L) - finish);
                if (millis > 0 && speed < 6000) {
                    Thread.sleep(millis);
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread thread = new Thread(new Wget(url, speed));
        thread.start();
        thread.join();
    }
}
