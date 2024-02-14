package ru.job4j.pools;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Find<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T findElement;

    public Find(T[] array, int from, int to, T findElement) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.findElement = findElement;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return List.of(array).indexOf(findElement);
        }
        int middle = (from + to) / 2;
        Find<T> leftSort = new Find<>(array, from, middle, findElement);
        Find<T> rightSort = new Find<>(array, middle + 1, to, findElement);
        leftSort.fork();
        rightSort.fork();
        Integer left = leftSort.join();
        Integer right = rightSort.join();
        return Math.max(left, right);
    }
    public static void main(String[] args) {
       Integer[] array = new Integer[60];
       for (int  i = 0; i < array.length; i++) {
           array[i] = i;
       }
        System.out.println(new ForkJoinPool().invoke(new Find<>(array, 0, array.length - 1, 20)));
    }
}