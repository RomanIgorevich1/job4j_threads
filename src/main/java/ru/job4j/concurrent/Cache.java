package ru.job4j.concurrent;

public final class Cache {
    private static Cache cache;

    public synchronized static Cache inst() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
