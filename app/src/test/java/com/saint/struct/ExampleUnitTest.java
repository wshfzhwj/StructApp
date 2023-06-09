package com.saint.struct;

import static org.junit.Assert.assertTrue;

import com.saint.struct.bean.Node;
import com.saint.struct.practice.InterviewFunc;
import com.saint.struct.practice.ListedIntegerNode;
import com.saint.struct.practice.SortPractice;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        SortPractice sortPractice = new SortPractice();
//        int[] array = {9, 8, 6, 7, 3, 5, 4};
//        sortPractice.bubbleSort(array);

        int[] a = new int[10];
        a[0] = 4;
        a[1] = 5;
        a[2] = 6;
        int[] b = {1, 2, 3};
        sortPractice.merge(a, b, 3, 3);
        print(a);
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

//        ChangeValues values = new ChangeValues();
//        values.change(values.str, values.ch);
//        System.out.println("a = " + values.str);
//        System.out.println(values.ch);
//        assertTrue(1 == 1);
//        testRevLinkedList();
//        testDeleteNode();
//            testHashMap();

//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        Iterator it = list.iterator();
//        System.out.println(it.toString());
//        Integer i = (Integer) it.next();
//        System.out.println(i);
//        it.remove();
//        System.out.println(Arrays.toString(list.toArray()));
    }

    public void testRevLinkedList() {
        Node<String> d = new Node<>("d", null);
        Node<String> c = new Node<>("C", d);
        Node<String> b = new Node<>("B", c);
        Node<String> a = new Node<>("A", b);
        Node<String> pre = new InterviewFunc().reverseList(a);
        System.out.println(pre.value);
    }

    public void testDeleteNode() {
        Node<String> d = new Node<>("d", null);
        Node<String> c = new Node<>("C", d);
        Node<String> b = new Node<>("B", c);
        Node<String> a = new Node<>("A", b);
        new InterviewFunc().deleteNode(a, c);
        System.out.println(b.next.value);
    }

    @Test
    public void testMaxSubArray() {
        int[] array = {-1, 4, -2, -6, 7, 3, 5, 4};
        InterviewFunc fun = new InterviewFunc();
        System.out.println(fun.maxSumArray(array));
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

    public void testHashMap() {
        HashMap<String, Integer> map = new HashMap();
        for (int i = 0; i < 25; i++) {
            map.put(String.valueOf(i), i);
        }
        try {
            //获取HashMap整个类
            Class<?> mapType = map.getClass();
            //获取指定属性，也可以调用getDeclaredFields()方法获取属性数组
            Field threshold = mapType.getDeclaredField("threshold");
            //将目标属性设置为可以访问
            threshold.setAccessible(true);
            //获取指定方法，因为HashMap没有容量这个属性，但是capacity方法会返回容量值
            Method capacity = mapType.getDeclaredMethod("capacity");
            //设置目标方法为可访问
            capacity.setAccessible(true);
            //打印刚初始化的HashMap的容量、阈值和元素数量
            System.out.println("容量：" + capacity.invoke(map) + "    阈值：" + threshold.get(map) + "    元素数量：" + map.size());

            System.out.println("size = " + map.size());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

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


    public class ChangeValues {
        String str = new String("Hello");
        char[] ch = {'a', 'b', 'c'};

        public void change(String str, char[] ch) {
            str = "Helloworld";
            ch[0] = 'b';
            ch[1] = 'b';
        }
    }

    public void changeValue(String str) {
        str = "abc";
    }


    @Test
    public void testLinkedAdd() {
        ListedIntegerNode n1 = new ListedIntegerNode();
        n1.data = 7;
        ListedIntegerNode n2 = new ListedIntegerNode();
        n2.data = 1;
        ListedIntegerNode n3 = new ListedIntegerNode();
        n3.data = 6;
        n2.next = n3;
        n1.next = n2;


        ListedIntegerNode n4 = new ListedIntegerNode();
        n4.data = 5;
        ListedIntegerNode n5 = new ListedIntegerNode();
        n5.data = 9;
        ListedIntegerNode n6 = new ListedIntegerNode();
        n6.data = 2;
        n5.next = n6;
        n4.next = n5;
        ListedIntegerNode node = addLists(n1,n4,0);
        while(node != null){
            System.out.println("node = " + node.data);
            node = node.next;
        }
    }

    ListedIntegerNode addLists(ListedIntegerNode l1, ListedIntegerNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }
        ListedIntegerNode result = new ListedIntegerNode();
        int value = carry;
        if (l1 != null) {
            value += l1.data;
        }
        if (l2 != null) {
            value += l2.data;
        }
        result.data = value % 10;

        ListedIntegerNode more = addLists(l1 == null ? null : l1.next, l2 == null ? null : l2.next, value >= 10 ? 1 : 0);
        result.next = more;
        return result;
    }

}