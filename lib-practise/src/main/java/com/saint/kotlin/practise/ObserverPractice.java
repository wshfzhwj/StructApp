package com.saint.kotlin.practise;

import java.util.ArrayList;

public class ObserverPractice {

    private ArrayList<ObserverInterface> list = new ArrayList<>();

    public void addObserver(ObserverInterface observerInterface) {
        list.add(observerInterface);
    }

    public void removeObserver(ObserverInterface observerInterface) {
        list.remove(observerInterface);
    }

    public void notifyChange() {
        for (ObserverInterface observerInterface : list) {
            observerInterface.change();
        }
    }

}
