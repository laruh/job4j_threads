package ru.job4j.pool;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IndexSearcherTest {

    @Test
    public void whenFindIndexOfLastElement() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        int elem = 29;
        int rsl = IndexSearcher.find(array, elem);
        assertThat(29, equalTo(rsl));
    }

    @Test
    public void whenFindIndex() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        int elem = 10;
        int rsl = IndexSearcher.find(array, elem);
        assertThat(10, equalTo(rsl));
    }

    @Test
    public void whenIndexNotFind() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        int elem = 42;
        int rsl = IndexSearcher.find(array, elem);
        assertThat(-1, equalTo(rsl));
    }
}