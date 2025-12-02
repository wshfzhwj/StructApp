package com.saint.kotlin.practise;

public class DoubleCheckSingleton {
    private volatile static DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    //第一次判断是为了避免不必要的同步
    //第二次判断是为了确保在此之前没有其他线程进入到synchronized块创建了新实例
    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {

            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }


}
