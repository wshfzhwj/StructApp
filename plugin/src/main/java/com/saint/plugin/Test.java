package com.saint.plugin;

public class Test {
    @ASMTest
    public void test() {
        long s = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long e = System.currentTimeMillis();
        System.out.println("execute time = " + (e - s) + "ms");
    }
}
