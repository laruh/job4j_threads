package ru.job4j.pool;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IndexSearcherTest {

    @Test
    public void whenFindIndexOfLastElement() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        int from = 15;
        int to = 30;
        int elem = 29;
        int rsl = IndexSearcher.find(array, from, to, elem);
        assertThat(29, equalTo(rsl));
    }

    @Test
    public void whenFindIndex() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        int from = 0;
        int to = 10;
        int elem = 10;
        int rsl = IndexSearcher.find(array, from, to, elem);
        assertThat(10, equalTo(rsl));
    }

    @Test
    public void whenIndexNotFind() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        int from = 0;
        int to = 15;
        int elem = 20;
        int rsl = IndexSearcher.find(array, from, to, elem);
        assertThat(-1, equalTo(rsl));
    }
}