package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearcher<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int fromInd;
    private final int toInd;
    private final T elem;

    public IndexSearcher(T[] array, int fromInd, int toInd, T elem) {
        this.array = array;
        this.fromInd = fromInd;
        this.toInd = toInd;
        this.elem = elem;
    }

    public int findIndex() {
        int rsl = -1;
        for (int i = fromInd; i <= toInd; i++) {
            if (elem.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (toInd - fromInd < 10) {
           return findIndex();
        }
        int mid = (fromInd + toInd) / 2;
        IndexSearcher<T> leftSearch = new IndexSearcher(array, fromInd, mid, elem);
        IndexSearcher<T> rightSearch = new IndexSearcher(array, mid + 1, toInd, elem);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    public static <T> int find(T[] array, T elem) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new IndexSearcher<T>(array, 0, array.length - 1, elem));
    }
}
