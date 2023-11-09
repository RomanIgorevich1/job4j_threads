package ru.job4j.io;

import java.io.*;

public class SaveContent {
    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
