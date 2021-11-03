package com.sf.struct.practice;

import java.util.ArrayList;

public class ObeservePractice {

    private ArrayList<OberInterface> list = new ArrayList<>();

    public void addOber(OberInterface oberInterface) {
        list.add(oberInterface);
    }

    public void removeOber(OberInterface oberInterface) {
        list.remove(oberInterface);
    }

    public void notifyChange() {
        for (OberInterface oberInterface : list) {
            oberInterface.change();
        }
    }

}
