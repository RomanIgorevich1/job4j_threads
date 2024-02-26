package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class FindTest {
    @Test
    public void whenLinearSearch() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertThat(Find.search(array, 4)).isEqualTo(3);
    }

    @Test
    public void whenRecursiveSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        assertThat(Find.search(array, 15)).isEqualTo(14);
    }

    @Test
    public void whenIndexNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        assertThat(Find.search(array, 22)).isEqualTo(-1);
    }

    @Test
    public void whenDifferentDataTypes() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        assertThat(Find.search(array, new Object())).isEqualTo(-1);
    }

    @Test
    public void whenSearchLastElement() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 22};
        assertThat(Find.search(array, 22)).isEqualTo(16);
    }
}