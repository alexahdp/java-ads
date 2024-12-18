package com.alexahdp.LinkedList;

public class LinkedList<T> {
    private int counter = 0;
    private Node<T> head;
    private Node<T> tail;

    public void add(T value) {
        if (head == null) {
            head = new Node(value, null);
            tail = head;
        } else {
            Node newNode = new Node(value, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        counter += 1;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) throws Exception {
        Node current = head;
        int i = 0;
        if (i > counter - 1) {
            throw new Exception("Access out of boundary element");
        }
        while (i < counter) {
            if (i == index) {
                return (T) current.getValue();
            }
            current = current.getNext();
            i += 1;
        }
        throw new Exception("Impossible situation");
    }

    @SuppressWarnings("unchecked")
    public boolean delete(int index) {
        Node current = head;
        Node prev = null;
        int i = 0;
        while (i < counter) {
            if (i == index) {
                if (prev != null) {
                    prev.setNext(current.getNext());
                } else {
                    head = current.getNext();
                }
                counter -= 1;
                return true;
            }
            i += 1;
            prev = current;
            current = current.getNext();
        }
        return false;
    }

    public int size() {
        return counter;
    }
}
