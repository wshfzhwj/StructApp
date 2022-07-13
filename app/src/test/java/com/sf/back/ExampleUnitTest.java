package com.sf.back;

import com.saint.struct.practice.InterviewFun;
import com.saint.struct.practice.SortPractice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int[] array = {9, 8, 6, 7, 3, 5, 4};
        SortPractice sortPractice = new SortPractice();
        sortPractice.bubbleSort(array);
//        sortPractice.selectSort(array);
//        sortPractice.insertSort(array);
//        sortPractice.quickSort(array);

//        int[] array = {3,4,3,4,6,3};
//        InterviewFun fun = new InterviewFun();
////        int a = fun.removeElement(array,3);
//        int a = fun.removeDuplicates(array);
//        print(array);
//        System.out.println("a = " + a);
//        assertEquals(4, 2 + 2);
        ChangeValues values = new ChangeValues();
        values.change(values.str,values.ch);
        System.out.println("a = " + values.str);
        System.out.println(values.ch);
        assertTrue(1 == 1);
    }

    @Test
    public void test() {
        int str = 123;
        change(str);
        System.out.println("str值为: " + str);  // str未被改变，str = "123"
    }

    public void change(int str) {
        str = 456;
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
    public void testList() {
        List<String> test = new ArrayList<String>();
        test.add("a");
        test.add("b");
        test.add("c");
        test.add("d");
        test.add("e");
        for (int i = 0; i < test.size(); i++) {
            test.remove(i);
        }
        System.out.println("剩余多少:" + test.size());
    }



    public class ChangeValues{
        String str  = new String("Hello");
        char[] ch = {'a','b','c'};

        public void change(String str,char[] ch) {
            str = "Helloworld";
            ch[0] = 'b';
            ch[1] = 'b';
        }
    }

    public void changeValue(String str) {
        str = "abc";
    }


}