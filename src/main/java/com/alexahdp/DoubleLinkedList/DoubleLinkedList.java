package com.alexahdp.DoubleLinkedList;

import com.alexahdp.DoubleLinkedList.Node;

public class DoubleLinkedList<T> {
    private int counter = 0;
    private Node<T> head;
    private Node<T> tail;

    public Node<T> add(T value) {
        var newNode = new Node<T>(value, null, null);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        counter += 1;
        return newNode;
    }

    public T get(int index) throws Exception {
        Node<T> current = head;
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

    public T find(T searchValue) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue() != null && current.getValue().equals(searchValue)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    public Node<T> findAndDelete(T searchValue) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue() != null && current.getValue().equals(searchValue)) {
                deleteNode(current);
                return current;
            }
            current = current.getNext();
        }
        return null;
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

    public void deleteNode(Node<T> current) {
        if (current == head) {
            head = current.getNext();
            if (head != null) {
                head.setPrev(null);
            } else {
                tail = null;
            }
        }
        else if (current == tail) {
            if (current.getPrev() != null) {
                current.getPrev().setNext(null);
                tail = current.getPrev();
            }
        } else {
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }
    }

    public int size() {
        return counter;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<T> current = this.head;
        while (current != null) {
            if (!result.isEmpty()) result.append(",");
            result.append(current.getValue());
            current = current.getNext();
        }
        return result.toString();
    }
}
