package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static  Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                int resultRow = 0;
                int resultCol = 0;
                for (int k = 0; k < matrix.length; k++) {
                    resultRow += matrix[i][k];
                    resultCol += matrix[k][i];
                }
                sums[i] = new Sums(resultRow, resultCol);
            }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(rowSum(matrix, i).get(), colSum(matrix, i).get());
        }
        return sums;
    }

    public static CompletableFuture<Integer> colSum(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int[] array : matrix) {
                sum += array[start];
            }
            return sum;
        });
    }

    public static CompletableFuture<Integer> rowSum(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
                for (int k = 0; k < matrix.length; k++) {
                    sum += matrix[start][k];
                }
            return sum;
        });
    }
}
