package ru.job4j.cas;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CASCountTest {

    @Test
    public void incrementAndGet() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get(), equalTo(2));
    }
}