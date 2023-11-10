package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class SingleLockListTest {
    @Test
    public void whenIt() {
        List<Integer> init = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        list.add(1);
        Iterator<Integer> it = list.iterator();
        list.add(2);
        assertThat(it.next()).isEqualTo(1);
    }

    @Test
    public void whenAdd() throws InterruptedException {
        List<Integer> init = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        Thread third = new Thread(() -> list.add(3));
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        Set<Integer> result = new TreeSet<>();
        list.iterator().forEachRemaining(result::add);
        assertThat(result).hasSize(3).containsAll(Set.of(1, 2, 3));
    }
}