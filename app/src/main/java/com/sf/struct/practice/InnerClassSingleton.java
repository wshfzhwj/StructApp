package com.sf.struct.practice;

public class InnerClassSingleton {
    private InnerClassSingleton() {
    }

    private static class SingletonHolder {
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }

    public static InnerClassSingleton getUniqueInstance() {
        return SingletonHolder.INSTANCE;
    }

}
