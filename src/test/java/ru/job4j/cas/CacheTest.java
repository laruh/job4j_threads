package ru.job4j.cas;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CacheTest {

    @Test
    public void whenAddTheSame() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base baseExisted = new Base(1, 0);
        assertTrue(cache.add(base));
        assertFalse(cache.add(baseExisted));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateException() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(1, 1);
        base.setName("Base");
        newBase.setName("New Base");
        cache.add(base);
        cache.update(newBase);
    }

    @Test
    public void whenUpdateTrue() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(1, 0);
        base.setName("Base");
        newBase.setName("New Base");
        cache.add(base);
        assertTrue(cache.update(newBase));
        assertThat(cache.getMemory().get(1).getName(), equalTo("New Base"));
        assertThat(cache.getMemory().get(1).getVersion(), equalTo(1));
    }

    @Test
    public void whenUpdateFalse() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(2, 0);
        cache.add(base);
        assertFalse(cache.update(newBase));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(2, 0);
        base1.setName("Base");
        base2.setName("New Base");
        cache.add(base1);
        cache.add(base2);
        cache.delete(new Base(2, 0));
        assertThat(cache.getMemory().size(), equalTo(1));
    }
}
