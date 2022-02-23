package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RolColSumTest {

    @Test
    public void getSumByIndex() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertThat(RolColSum.getSumByIndex(matrix, 1).getRowSum(), equalTo(15));
        assertThat(RolColSum.getSumByIndex(matrix, 1).getColSum(), equalTo(15));
    }

    @Test
    public void whenSums() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertThat(RolColSum.sums(matrix).length, equalTo(3));
        assertThat(RolColSum.sums(matrix)[2].getColSum(), equalTo(18));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertThat(RolColSum.asyncSum(matrix).length, equalTo(3));
        assertThat(RolColSum.sums(matrix)[0].getRowSum(), equalTo(6));
    }
}