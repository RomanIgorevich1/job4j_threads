package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class RolColSumTest {
      private final int[][] array = new int[][]{
              {1, 2, 3},
              {4, 5, 6},
              {7, 8, 9}
      };


    @Test
    public void whenFindColSum() {
        List<RolColSum.Sums> list = new ArrayList<>(Arrays.asList(RolColSum.sum(array)));
        assertThat(list.get(0).getColSum()).isEqualTo(12);
    }

    @Test
    public void whenFindRowSum() {
        List<RolColSum.Sums> list = new ArrayList<>(Arrays.asList(RolColSum.sum(array)));
        assertThat(list.get(2).getRowSum()).isEqualTo(24);
    }

    @Test
    public void whenFindAsyncColSum() throws ExecutionException, InterruptedException {
        List<RolColSum.Sums> list = new ArrayList<>(Arrays.asList(RolColSum.asyncSum(array)));
        assertThat(list.get(1).getColSum()).isEqualTo(15);
    }

    @Test
    public void whenFindAsyncRowSum() throws ExecutionException, InterruptedException {
        List<RolColSum.Sums> list = new ArrayList<>(Arrays.asList(RolColSum.asyncSum(array)));
        assertThat(list.get(0).getRowSum()).isEqualTo(6);
    }

}