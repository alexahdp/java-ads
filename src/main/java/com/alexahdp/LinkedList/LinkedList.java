package com.alexahdp.LinkedList;

public class LinkedList<T> {
    private int counter = 0;
    private Node<T> head;
    private Node<T> tail;

    public Node<T> add(T value) {
        var newNode = new Node<T>(value, null);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        counter += 1;
        return newNode;
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
        Node<T> prev = null;
        while (current != null) {
            if (current.getValue() != null && current.getValue().equals(searchValue)) {
                if (current == head) {
                    head = current.getNext();
                    if (current == tail) {
                        tail = null;
                    }
                }
                else if (current == tail) {
                    if (prev != null) {
                        prev.setNext(null);
                    } else {
                        head = null;
                        tail = null;
                    }
                } else {
                    prev.setNext(current.getNext());
                }
                return current;
            }
            prev = current;
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

    public void deleteNode(Node<T> node) {
        if (node == null) {
            return;
        }
        if (head == node) {
            head = head.getNext();
            return;
        }
        if (tail == node) {
            // TODO: Fuck, I need doule-directed linked list!
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
