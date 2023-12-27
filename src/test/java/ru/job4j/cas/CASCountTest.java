package ru.job4j.cas;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

 public class CASCountTest {
     @Test
     void whenIncrementNumber() throws InterruptedException {
         CASCount count = new CASCount();
         Thread thread1 = new Thread(() -> {
             for (int i = 0; i < 1000; i++) {
                 count.increment();
             }
         });
         Thread thread2 = new Thread(() -> {
             for (int i = 0; i < 1000; i++) {
                 count.increment();
             }
         });
         thread1.start();
         thread2.start();
         thread1.join();
         thread2.join();
         assertThat(count.get()).isEqualTo(2000);
     }
}