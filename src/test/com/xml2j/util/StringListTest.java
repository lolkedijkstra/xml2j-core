package com.xml2j.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringListTest {

    @Test
    void equals() {
        StringList l1 = new StringList("l1");
        l1.add("one");
        l1.add("two");

        StringList l2 = new StringList("l2");
        l2.add("one");
        l2.add("two");

        Assertions.assertFalse( l1.equals(l2) );

        StringList l3 = new StringList("l2");
        l3.add("one");
        l3.add("two");

        Assertions.assertTrue( l2.equals(l3) );

        StringList l4 = new StringList("l2");
        l3.add("one");
        l3.add("three");

        Assertions.assertFalse( l3.equals(l4) );
    }
}