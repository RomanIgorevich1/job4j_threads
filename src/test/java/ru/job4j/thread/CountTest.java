package ru.job4j.thread;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.getValue()).isEqualTo(2);
    }
}