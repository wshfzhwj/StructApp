package com.sf.back;

import com.sf.struct.practice.InterviewFun;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        int[] array = {9, 8, 6, 7, 3, 5, 4};
//        SortPractice sortPractice = new SortPractice();
//        sortPractice.bubbleSort(array);
//        sortPractice.selectSort(array);
//        sortPractice.insertSort(array);
//        sortPractice.quickSort(array);

        int[] array = {3,4,3,4,6,3};
        InterviewFun fun = new InterviewFun();
//        int a = fun.removeElement(array,3);
        int a = fun.removeDuplicates(array);
        print(array);
        System.out.println("a = " + a);
        assertEquals(4, 2 + 2);
    }

    public void print(int[] array) {
        System.out.print(Arrays.toString(array));

    }

    @Test
    public void testInteger() {
        Integer a = 128;
        Integer b = 128;
        assertTrue(a == b);
    }

    @Test
    public void testStr() {
        String str = "123";
        changeValue(str);
        System.out.println("str值为: " + str);  // str未被改变，str = "123"
    }

    public void changeValue(String str) {
        str = "abc";
    }
}