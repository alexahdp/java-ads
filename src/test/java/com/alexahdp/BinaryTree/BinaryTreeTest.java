package com.alexahdp.BinaryTree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BinaryTreeTest {

    @Test
    void createBinaryTree() {
        var btree = new BinaryTree<String>();
        Assertions.assertThat(btree.isEmpty()).isTrue();
    }

    @Test
    void createBinaryTreeAndAdd1Element() {
        var btree = new BinaryTree<Integer>();
        btree.add("1", 1);
        Assertions.assertThat(btree.isEmpty()).isFalse();
        Optional<Integer> optionalValue = btree.get("1");
        Assertions.assertThat(optionalValue.isEmpty()).isFalse();
        optionalValue.ifPresent(value -> Assertions.assertThat(value).isEqualTo(1));
    }

    @Test
    void getDepth_0() {
        var btree = new BinaryTree<Integer>();
        Assertions.assertThat(btree.getDepth()).isEqualTo(0);
    }

    @Test
    void getDepth_1() {
        var btree = new BinaryTree<Integer>();
        btree.add("1", 1);
        Assertions.assertThat(btree.getDepth()).isEqualTo(1);
    }

    @Test
    void getDepth_2() {
        var btree = new BinaryTree<Integer>();
        btree.add("2", 1);
        btree.add("1", 1);
        btree.add("3", 1);
        Assertions.assertThat(btree.getDepth()).isEqualTo(2);
    }

    @Test
    void getDepth_3() {
        var btree = new BinaryTree<Integer>();
        btree.add("5", 1);
        btree.add("3", 1);
        btree.add("7", 1);
        btree.add("1", 1);
        Assertions.assertThat(btree.getDepth()).isEqualTo(3);
    }
}
