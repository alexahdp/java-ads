package com.alexahdp.LinkedList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node<T> {
    private Node<T> next;
    private T value;

    public Node (T value, Node<T> next) {
        this.next = next;
        this.value = value;
    }
}
