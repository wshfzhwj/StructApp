package com.saint.struct.practice;

import android.util.Log;

public class InterviewFun {
    public void lightFun() {
        boolean[] light = new boolean[100];
        for (int i = 0; i < 100; i++) {
            for (int j = i; j < 100; j = j + i + 1) {
                light[j] = !light[j];
            }
        }

        for(int i= 0;i<100;i++){
            if(light[i]){
//                System.out.print(i + " ");
                Log.d("light",i + 1 + " ");
            }
        }
    }



    public int removeElement(int[] A, int elem) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int index = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != elem) {
                A[index++] = A[i];
            }
        }

        return index;
    }


    public int removeDuplicates(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int size = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != A[size]) {
                A[++size] = A[i];
            }
        }
        return size + 1;
    }
}
