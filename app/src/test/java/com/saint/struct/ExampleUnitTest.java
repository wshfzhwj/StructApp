package com.saint.struct;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        String invertedOddNumbers = numbers
                .stream()
                .filter(it -> it % 2 != 0)
                .map(it -> -it)
                .map(Object::toString)
                .collect(Collectors.joining("; "));
        System.out.println(invertedOddNumbers);
        assert ((2 + 2) == 4);
    }
}