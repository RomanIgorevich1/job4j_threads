package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final File file;

    public Wget(String url, int speed, File file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long downloadedSize = 0;
            long timeStart = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                System.out.println(file.length() + " file size");
                if ((file.length() - downloadedSize) > 1000) {
                    long finish = (System.currentTimeMillis() - timeStart);
                    System.out.println(finish + " millis");
                    downloadedSize += speed;
                    if (finish < 1000) {
                        Thread.sleep(1000 - finish);
                        System.out.println(finish + " sleep");
                    }
                }
                System.out.println(downloadedSize);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void validation(String[] args) {
        if (!args[1].matches("^\\d{4}$")) {
            throw new IllegalArgumentException("Wrong format of argument.");
        }
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong number of arguments.");
        }
        try {
            new URL(args[0]).toURI();
        } catch (URISyntaxException | MalformedURLException exception) {
            System.out.println("URL is not valid");
            exception.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        validation(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        File file = new File(args[2]);
        Thread thread = new Thread(new Wget(url, speed, file));
        thread.start();
        thread.join();
    }
}
