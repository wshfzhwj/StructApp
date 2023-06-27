package com.saint.kotlin.practise

import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.InvocationTargetException
import java.util.Arrays

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val sortPractice = SortPractice()
        //        int[] array = {9, 8, 6, 7, 3, 5, 4};
//        sortPractice.bubbleSort(array);
        val a = IntArray(10)
        a[0] = 4
        a[1] = 5
        a[2] = 6
        val b = intArrayOf(1, 2, 3)
        sortPractice.merge(a, b, 3, 3)
        print(a)
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

    fun testRevLinkedList() {
        val d: Node<String> = Node("d", null)
        val c: Node<String> = Node("C", d)
        val b: Node<String> = Node("B", c)
        val a: Node<String> = Node("A", b)
        val pre: Node<String> = InterviewFunc().reverseList(a)
        System.out.println(pre.value)
    }

    fun testDeleteNode() {
        val d: Node<String> = Node("d", null)
        val c: Node<String> = Node("C", d)
        val b: Node<String> = Node("B", c)
        val a: Node<String> = Node("A", b)
        InterviewFunc().deleteNode(a, c)
        System.out.println(b.next.value)
    }

    @Test
    fun testMaxSubArray() {
        val array = intArrayOf(-1, 4, -2, -6, 7, 3, 5, 4)
        val `fun` = InterviewFunc()
        System.out.println(`fun`.maxSumArray(array))
    }

    @Test
    fun test() {
        val str = 123
        change(str)
        println("str值为: $str") // str未被改变，str = "123"
    }

    fun change(str: Int) {
        var str = str
        str = 456
    }

    fun print(array: IntArray?) {
        print(Arrays.toString(array))
    }

    fun testHashMap() {
        val map: HashMap<String?, Int?> = HashMap()
        for (i in 0..24) {
            map[i.toString()] = i
        }
        try {
            //获取HashMap整个类
            val mapType: Class<*> = map.javaClass
            //获取指定属性，也可以调用getDeclaredFields()方法获取属性数组
            val threshold = mapType.getDeclaredField("threshold")
            //将目标属性设置为可以访问
            threshold.isAccessible = true
            //获取指定方法，因为HashMap没有容量这个属性，但是capacity方法会返回容量值
            val capacity = mapType.getDeclaredMethod("capacity")
            //设置目标方法为可访问
            capacity.isAccessible = true
            //打印刚初始化的HashMap的容量、阈值和元素数量
            println("容量：" + capacity.invoke(map) + "    阈值：" + threshold[map] + "    元素数量：" + map.size)
            println("size = " + map.size)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testInteger() {
        val a = 128
        val b = 128
        assertTrue(a === b)
    }


    @Test
    fun testList() {
        val test: MutableList<String> = ArrayList()
        test.add("a")
        test.add("b")
        test.add("c")
        test.add("d")
        test.add("e")
        for (i in test.indices) {
            test.removeAt(i)
        }
        println("剩余多少:" + test.size)
    }


    class ChangeValues {
        var str = "Hello"
        var ch = charArrayOf('a', 'b', 'c')
        fun change(str: String?, ch: CharArray) {
            var str = str
            str = "Helloworld"
            ch[0] = 'b'
            ch[1] = 'b'
        }
    }


    fun changeValue(str: String?) {
        var str = str
        str = "abc"
    }


    @Test
    fun testLinkedAdd() {
        val n1 = ListedIntegerNode()
        n1.data = 7
        val n2 = ListedIntegerNode()
        n2.data = 1
        val n3 = ListedIntegerNode()
        n3.data = 6
        n2.next = n3
        n1.next = n2
        val n4 = ListedIntegerNode()
        n4.data = 5
        val n5 = ListedIntegerNode()
        n5.data = 9
        val n6 = ListedIntegerNode()
        n6.data = 2
        n5.next = n6
        n4.next = n5
        var node: ListedIntegerNode? = addLists(n1, n4, 0)
        while (node != null) {
            System.out.println("node = " + node.data)
            node = node.next
        }
    }

    fun addLists(l1: ListedIntegerNode?, l2: ListedIntegerNode?, carry: Int): ListedIntegerNode? {
        if (l1 == null && l2 == null && carry == 0) {
            return null
        }
        val result = ListedIntegerNode()
        var value = carry
        if (l1 != null) {
            value += l1.data
        }
        if (l2 != null) {
            value += l2.data
        }
        result.data = value % 10
        val more: ListedIntegerNode? =
            addLists(if (l1 == null) null else l1.next, if (l2 == null) null else l2.next, if (value >= 10) 1 else 0)
        result.next = more
        return result
    }

}