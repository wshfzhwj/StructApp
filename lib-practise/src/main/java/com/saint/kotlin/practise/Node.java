package com.saint.kotlin.practise;

public class Node<T> {
    public T value;
    public Node<T> next;

    public Node() {
    }

    public Node(T v, Node<T> next) {
        this.value = v;
        this.next = next;
    }
}
