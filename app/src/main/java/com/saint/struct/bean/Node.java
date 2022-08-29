package com.saint.struct.bean;

public class Node<T> {
    public T value;
    public Node<T> next;

    public Node() {
    }

    public Node(T v, Node next) {
        this.value = v;
        this.next = next;
    }
}
