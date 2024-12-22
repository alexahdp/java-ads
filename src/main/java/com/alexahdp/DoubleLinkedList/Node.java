package com.alexahdp.DoubleLinkedList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node<T> {
    private Node<T> next;
    private Node<T> prev;
    private T value;

    public Node (T value, Node<T> next, Node<T> prev) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }
}
