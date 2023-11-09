package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter)  {
        StringBuilder builder = new StringBuilder();
        int data;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((data = reader.read()) != -1) {
                char output = (char) data;
                if (filter.test(output)) {
                    builder.append(output);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return builder.toString();
    }

    public String getContentWithoutUnicode()  {
        return getContent(character -> character < 0x80);
    }
}