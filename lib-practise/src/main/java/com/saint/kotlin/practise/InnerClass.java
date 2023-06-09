package com.saint.kotlin.practise;

public class InnerClass {
    static int d = 60;
    int f = 30;
    public class World{
        public static final int c=20;
        public void hello(){
            System.out.println("hello 我是普通内部类"+f+d);
        }
    }
    public static class  Hello{
        public static int a =10;
        public int b = 10;
        public void hello(){
            System.out.println("hello 我是静态内部类"+d);
        }
        public static void world(){
            System.out.println("world");
        }
    }
}
