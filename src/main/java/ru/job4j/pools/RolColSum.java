package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                sums[i] = new Sums();
                for (int k = 0; k < matrix.length; k++) {
                    sums[i].rowSum += matrix[i][k];
                    sums[i].colSum += matrix[k][i];
                }
            }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(rowSum(matrix, i).get());
            sums[i].setColSum(colSum(matrix, i).get());
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
