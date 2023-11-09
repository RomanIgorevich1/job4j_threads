package ru.job4j.ref;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    /**
     * В кеш добавляем копию объекта
     * @param user
     */

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    /**
     *
     * @param id
     * @return Возвращаем копию объекта
     */
    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        return users.values()
                .stream()
                .map((user) -> User.of(user.getName()))
                .toList();
    }
}
