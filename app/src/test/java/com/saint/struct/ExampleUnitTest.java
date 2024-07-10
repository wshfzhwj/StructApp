package com.saint.struct;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        String a = "a";
        System.out.println("-------"+ System.identityHashCode(a));
        a = "-----------b";
        System.out.println("-------"+ System.identityHashCode(a));
        assert ((2 + 2) == 4);
    }
}