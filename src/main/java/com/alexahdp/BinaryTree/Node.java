package com.alexahdp.BinaryTree;

import lombok.Getter;
import lombok.Setter;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Node<T> {
    @Setter
    @Getter
    private Node<T> left;

    @Setter
    @Getter
    private Node<T> right;

    @Setter
    @Getter
    private String key;

    @Setter
    @Getter
    private T value;

    public Node(String key, T value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node<T> get(String key) {
        int res = key.compareTo(this.getKey());

        if (res == 0) {
            return this;
        } else if (res < 0) {
            if (this.getLeft() == null) {
                return null;
            } else {
                return this.getLeft().get(key);
            }
        } else {
            if (this.getRight() == null) {
                return null;
            } else {
                return this.getRight().get(key);
            }
        }
    }

    public boolean add(Node<T> node) {
        Node<T> currentNode = this;

        int res = node.getKey().compareTo(currentNode.getKey());
        if (res == 0) {
            currentNode.setValue(value);
            return false;
        } // "a".compareTo("b");
        else if (res < 0) {
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(node);
                return true;
            } else {
                if (currentNode.getLeft().add(node)) {
                    currentNode.balance();
                    return true;
                } else {
                    return false;
                }
            }
        } // "b".compareTo("a");
        else {
            if (currentNode.getRight() == null) {
                currentNode.setRight(node);
                return true;
            } else {
                if (currentNode.getRight().add(node)) {
                    currentNode.balance();
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean remove(String key, Node<T> parent) {
        Node<T> currentNode = this;

        int res = key.compareTo(currentNode.getKey());
        if (res == 0) {
            if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                if (parent.getLeft() == currentNode) {
                    parent.setLeft(null);
                    return true;
                } else {
                    parent.setRight(null);
                    return true;
                }
            } else {
                var p = currentNode.getDepth();
                int leftDepth = p.getValue0();
                int rightDepth = p.getValue1();
                if (leftDepth > rightDepth) {
                    Node<T> maxNode = currentNode.getLeft().findMax();
                    currentNode.setKey(maxNode.getKey());
                    currentNode.getLeft().remove(maxNode.getKey(), currentNode);
                    currentNode.getLeft().balance();
                    return true;
                } else {
                    Node<T> minNode = currentNode.getRight().findMin();
                    currentNode.setKey(minNode.getKey());
                    currentNode.getRight().remove(minNode.getKey(), currentNode);
//                    currentNode.getRight().balance();
                    return true;
                }
            }
        } else if (res < 0) {
            if (currentNode.getLeft() == null) {
                return false;
            } else {
                if (currentNode.getLeft().remove(key, currentNode)) {
                    currentNode.balance();
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (currentNode.getRight() == null) {
                return false;
            } else {
                if (currentNode.getRight().remove(key, currentNode)) {
                    currentNode.balance();
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public void balance() {
        var p = this.getDepth();
        int leftDepth = p.getValue0();
        int rightDepth = p.getValue1();

        if (Math.abs(leftDepth - rightDepth) >= 2) {
            if (leftDepth > rightDepth) {
                p = this.getLeft().getDepth();
                int leftDepthLeft = p.getValue0();
                int rightDepthLeft = p.getValue1();
                if (leftDepthLeft < rightDepthLeft) {
                    this.getLeft().rotateLeft();
                }
                this.rotateRight();
            } else {
                p = this.getRight().getDepth();
                int leftDepthRight = p.getValue0();
                int rightDepthRight = p.getValue1();
                if (leftDepthRight > rightDepthRight) {
                    this.getRight().rotateRight();
                }
                this.rotateLeft();
            }
        }
    }

    public Pair<Integer, Integer> getDepth() {
        int leftDepth = 1;
        int rightDepth = 1;

        if (this.getLeft() != null) {
            var p = this.getLeft().getDepth();
            leftDepth = Math.max(p.getValue0(), p.getValue1()) + 1;
        }
        if (this.getRight() != null) {
            var p = this.getRight().getDepth();
            rightDepth = Math.max(p.getValue0(), p.getValue1()) + 1;
        }
        return new Pair<>(leftDepth, rightDepth);
    }

    public void rotateLeft() {
        String key = this.getKey();
        T value = this.getValue();
        var right = this.getRight();

        this.setKey(right.getKey());
        this.setValue(right.getValue());
        this.setRight(right.getRight());

        Node<T> newNode = new Node<T>(key, value);
        newNode.setLeft(this.getLeft());
        newNode.setRight(right.getLeft());
        this.setLeft(newNode);
    }

    public void rotateRight() {
        String key = this.getKey();
        T value = this.getValue();
        var left = this.getLeft();

        this.setKey(left.getKey());
        this.setValue(left.getValue());
        this.setLeft(left.getLeft());

        Node<T> newNode = new Node<T>(key, value);
        newNode.setRight(this.getRight());
        newNode.setLeft(left.getRight());
        this.setRight(newNode);
    }

    public String toString() {
        LinkedList<String> result = this.serialize(null);
        return String.join(",", result);
    }

    private LinkedList<String> serialize(LinkedList<String> result) {
        if (result == null) {
            result = new LinkedList<String>();
        }
        result.add(this.getKey());
        if (this.getLeft() == null) {
            result.add("null");
        } else {
            this.getLeft().serialize(result);
        }
        if (this.getRight() == null) {
            result.add("null");
        } else {
            this.getRight().serialize(result);
        }
        return result;
    }

    static public <T> Node<T> fromString(String serialized) {
        LinkedList<String> items = Arrays.stream(serialized.split(","))
            .collect(Collectors.toCollection(LinkedList::new));
        if (items.size() == 0) {
            return null;
        }
        return deserialize(items);
    }

    static private <T> Node<T> deserialize(LinkedList<String> items) {
        String value = items.poll();

        if (value.equals("null")) {
            return null;
        }

        var node = new Node<T>(value, null);

        Node<T> leftNode = deserialize(items);
        node.setLeft(leftNode);

        Node<T> rightNode = deserialize(items);
        node.setRight(rightNode);

        return node;
    }

    public boolean equals(Node<T> node) {
        if (!this.getKey().equals(node.getKey())) {
            return false;
        }
        if (this.getValue() != null && node.getValue() == null || this.getValue() == null && node.getValue() != null) {
            return false;
        }
        if (this.getValue() != null && !this.getValue().equals(node.getValue())) {
            return false;
        }
        if (
            (this.getLeft() == null) && (node.getLeft() != null) ||
            (this.getLeft() != null) && (node.getLeft() == null)
        ) {
            return false;
        }
        if (this.getLeft() != null && !this.getLeft().equals(node.getLeft())) {
            return false;
        }
        if (
            (this.getRight() == null) && (node.getRight() != null) ||
            (this.getRight() != null) && (node.getRight() == null)
        ) {
            return false;
        }
        if (this.getRight() != null && !this.getRight().equals(node.getRight())) {
            return false;
        }
        return true;
    }

    public Node<T> findMax() {
        if (this.getRight() == null) {
            return this;
        } else {
            return this.getRight().findMax();
        }
    }

    public Node<T> findMin() {
        if (this.getLeft() == null) {
            return this;
        } else {
            return this.getLeft().findMin();
        }
    }
}
