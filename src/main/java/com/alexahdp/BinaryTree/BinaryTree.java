package com.alexahdp.BinaryTree;

import java.util.Optional;

/**
 * Binary tree implementation with next operations:
 *  - create
 *  - add
 *  - get
 *  - delete
 */
public class BinaryTree<T> {
    private Node<T> root = null;

    public void add(String key, T value) {
        if (root == null) {
            root = new Node<T>(key, value);
        } else {
            var newNode = new Node<T>(key, value);
            if (root.add(newNode)) {
                root.balance();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<T> get(String key) {
        if (root == null) {
            return Optional.empty();
        }
        Node<T> node = root.get(key);
        return Optional.ofNullable(node != null ? node.getValue() : null);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getDepth() {
        if (this.root == null) {
            return 0;
        }
        var p = this.root.getDepth();
        return Math.max(p.getValue0(), p.getValue1());
    }
}
