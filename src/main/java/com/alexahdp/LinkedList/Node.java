package com.alexahdp.LinkedList;

import lombok.Getter;
import lombok.Setter;

public class Node<T> {
    @Setter
    @Getter
    private Node next;

    @Setter
    @Getter
    private T value;

    public Node (T value, Node next) {
        this.next = next;
        this.value = value;
    }
}
