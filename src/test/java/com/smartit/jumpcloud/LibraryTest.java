package com.smartit.jumpcloud;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LibraryTest {

    Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void test_AddAction_one_entry() {
        library.addAction("{\"action\":\"jump\",\"time\":100}");
        assertTrue(library.counter.containsKey("jump"));
    }

    @Test
    public void test_AddAction_null_param() {
        library.addAction(null);
        assertTrue(library.counter.isEmpty());
    }

    @Test
    public void test_AddAction_empty_param() {
        library.addAction("");
        assertTrue(library.counter.isEmpty());
    }

    @Test
    public void test_getStats_is_empty() {
        System.out.println(library.getStats());
        assertTrue(library.getStats().isEmpty());
    }

    @Test
    public void test_getStats_has_content() {
        library.addAction("{\"action\":\"jump\",\"time\":100}");
        assertFalse(library.getStats().isEmpty());
    }
}