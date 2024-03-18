package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class RolColSumTest {
      private final int[][] array = new int[][]{
              {1, 2, 3},
              {4, 5, 6},
              {7, 8, 9}
      };

      private final Sums[] sums = new Sums[] {
              new Sums(6, 12),
              new Sums(15, 15),
              new Sums(24, 18)
      };


    @Test
    public void whenEqualArray() {
        assertThat(sums).isEqualTo(RolColSum.sum(array));
    }

    @Test
    public void whenFindColSum() {
        assertThat(sums[1].getColSum()).isEqualTo(RolColSum.sum(array)[1].getColSum());
    }

    @Test
    public void whenFindRowSum() {
        assertThat(sums[2].getRowSum()).isEqualTo(RolColSum.sum(array)[2].getRowSum());
    }

    @Test
    public void whenFindAsyncColSum() throws ExecutionException, InterruptedException {
        assertThat(sums[1].getColSum()).isEqualTo(RolColSum.asyncSum(array)[1].getColSum());
    }

    @Test
    public void whenFindAsyncRowSum() throws ExecutionException, InterruptedException {
        assertThat(sums[2].getRowSum()).isEqualTo(RolColSum.asyncSum(array)[2].getRowSum());
    }

}