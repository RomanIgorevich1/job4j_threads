package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


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
            long sizeCheck = 0;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long timeStart = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                long finish = (System.nanoTime() - timeStart);
                System.out.println("Read 1024 bytes : "  + finish + " nano");
                long millis = TimeUnit.NANOSECONDS.toMillis((speed * 1000000L) - finish);
                if (sizeCheck == 0) {
                    sizeCheck = file.length();
                } else {
                    sizeCheck += (file.length() - (downloadedSize + sizeCheck));
                }
                if (sizeCheck > speed) {
                    Thread.sleep(millis);
                    sizeCheck -= speed;
                    downloadedSize += speed;
                }
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
