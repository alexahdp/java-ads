package com.alexahdp.BinaryTree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class NodeTest {
    @Test
    void createNode() {
        var node = new Node<Integer>("1", 1);
        Assertions.assertThat(node.getKey()).isEqualTo("1");
        Assertions.assertThat(node.getValue()).isEqualTo(1);
        Assertions.assertThat(node.getLeft()).isNull();
        Assertions.assertThat(node.getRight()).isNull();
    }

    @Test
    void simpleTree() {
        var node1 = new Node<Integer>("1", 1);
        var node2 = new Node<Integer>("2", 2);
        var node3 = new Node<Integer>("3", 3);
        node2.add(node1);
        node2.add(node3);
        Assertions.assertThat(node2.getLeft()).isEqualTo(node1);
        Assertions.assertThat(node2.getRight()).isEqualTo(node3);
    }

    @Test
    void serialize_1() {
        var node2 = new Node<Integer>("2", 2);
        node2.add(new Node<Integer>("1", 1));
        node2.add(new Node<Integer>("3", 3));
        String serialized = node2.toString();
        Assertions.assertThat(serialized).isEqualTo("2,1,null,null,3,null,null");
    }

    @Test
    void serialize_2() {
        var node1 = new Node<Integer>("1", 1);
        var node3 = new Node<Integer>("3", 3);
        var node5 = new Node<Integer>("5", 5);
        var node6 = new Node<Integer>("6", 6);
        var node7 = new Node<Integer>("7", 7);
        var node9 = new Node<Integer>("9", 9);
        node5.setLeft(node3);
        node5.setRight(node7);
        node3.setLeft(node1);
        node7.setLeft(node6);
        node7.setRight(node9);
        String serialized = node5.toString();
        Assertions.assertThat(serialized).isEqualTo("5,3,1,null,null,null,7,6,null,null,9,null,null");
    }

    @Test
    void deserialize_1() {
        String serialized = "2,1,null,null,3,null,null";
        Node<Integer> node = Node.fromString(serialized);
        Assertions.assertThat(node).isNotNull();
        Assertions.assertThat(node.getKey()).isEqualTo("2");
        Assertions.assertThat(node.getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node.getRight().getKey()).isEqualTo("3");
    }

    @Test
    void deserialize_2() {
        String serialized = "5,3,1,null,null,null,7,6,null,null,9,null,null";
        Node<Integer> node = Node.fromString(serialized);

        Assertions.assertThat(node).isNotNull();
        Assertions.assertThat(node.getKey()).isEqualTo("5");
        Assertions.assertThat(node.getLeft().getKey()).isEqualTo("3");
        Assertions.assertThat(node.getRight().getKey()).isEqualTo("7");
        Assertions.assertThat(node.getLeft().getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node.getLeft().getLeft().getLeft()).isNull();
        Assertions.assertThat(node.getLeft().getLeft().getRight()).isNull();
        Assertions.assertThat(node.getLeft().getRight()).isNull();
        Assertions.assertThat(node.getRight().getLeft().getKey()).isEqualTo("6");
        Assertions.assertThat(node.getRight().getLeft().getLeft()).isNull();
        Assertions.assertThat(node.getRight().getLeft().getRight()).isNull();
        Assertions.assertThat(node.getRight().getRight().getKey()).isEqualTo("9");
        Assertions.assertThat(node.getRight().getRight().getLeft()).isNull();
        Assertions.assertThat(node.getRight().getRight().getRight()).isNull();
    }

    @Test
    void getDepth_1() {
        var node = new Node<Integer>("1", 1);
        var p = node.getDepth();
        Assertions.assertThat(p.getValue0()).isEqualTo(1);
        Assertions.assertThat(p.getValue1()).isEqualTo(1);
    }

    @Test
    void getDepth_21() {
        var node = new Node<Integer>("3", 1);
        node.setLeft(new Node<Integer>("1", 1));
        var p = node.getDepth();
        Assertions.assertThat(p.getValue0()).isEqualTo(2);
        Assertions.assertThat(p.getValue1()).isEqualTo(1);
    }

    @Test
    void getDepth_22() {
        var node = new Node<Integer>("3", 1);
        node.setLeft(new Node<Integer>("1", 1));
        node.setRight(new Node<Integer>("5", 1));
        var p = node.getDepth();
        Assertions.assertThat(p.getValue0()).isEqualTo(2);
        Assertions.assertThat(p.getValue1()).isEqualTo(2);
    }

    @Test
    void rotateLeft() {
        var node1 = new Node<Integer>("1", 1);
        var node2 = new Node<Integer>("2", 1);
        var node3 = new Node<Integer>("3", 1);
        node1.add(node2);
        node2.add(node3);
        node1.rotateLeft();

        Assertions.assertThat(node1.getKey()).isEqualTo("2");
        Assertions.assertThat(node1.getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node1.getRight().getKey()).isEqualTo("3");
    }

    @Test
    void rotateRight() {
        var node1 = new Node<Integer>("1", 1);
        var node2 = new Node<Integer>("2", 1);
        var node3 = new Node<Integer>("3", 1);
        node3.add(node2);
        node2.add(node1);
        node3.rotateRight();

        Assertions.assertThat(node3.getKey()).isEqualTo("2");
        Assertions.assertThat(node3.getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node3.getRight().getKey()).isEqualTo("3");
    }

    @Test
    void balance_1() {
        var node1 = new Node<Integer>("1", 1);
        var node2 = new Node<Integer>("2", 1);
        var node3 = new Node<Integer>("3", 1);
        node1.add(node2);
        node2.add(node3);
        node1.balance();

        Assertions.assertThat(node1.getKey()).isEqualTo("2");
        Assertions.assertThat(node1.getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node1.getRight().getKey()).isEqualTo("3");
    }

    @Test
    void balance_2() {
        var node1 = new Node<Integer>("3", 1);
        var node2 = new Node<Integer>("2", 1);
        var node3 = new Node<Integer>("1", 1);
        node3.add(node2);
        node2.add(node1);
        node3.balance();

        Assertions.assertThat(node3.getKey()).isEqualTo("2");
        Assertions.assertThat(node3.getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node3.getRight().getKey()).isEqualTo("3");
    }

    @Test
    void balance_3() {
        var node = Node.fromString("5,3,1,null,null,null,null");
        node.balance();

        Assertions.assertThat(node.getKey()).isEqualTo("3");
        Assertions.assertThat(node.getLeft().getKey()).isEqualTo("1");
        Assertions.assertThat(node.getRight().getKey()).isEqualTo("5");
    }

//    @Test
//    void remove_1() {
//        var node = Node.fromString("5,3,1,null,null,null,7,null,null");
//        node.remove("1");
//        Assertions.assertThat(node.getKey()).isEqualTo("3");
//        Assertions.assertThat(node.getLeft()).isNull();
//        Assertions.assertThat(node.getRight().getKey()).isEqualTo("5");
//    }

    @Test
    void equals_1() {
        var node1 = Node.fromString("5,3,1,null,null,null,7,null,null");
        var node2 = Node.fromString("5,3,1,null,null,null,7,null,null");
        Assertions.assertThat(node1.equals(node2)).isTrue();
    }

    @Test
    void equals_2() {
        var node1 = Node.fromString("5,3,1,null,null,null,7,6,null,null,null");
        var node2 = Node.fromString("5,3,1,null,null,null,7,6,null,null,null");
        Assertions.assertThat(node1.equals(node2)).isTrue();
    }

    @Test
    void equals_3() {
        var node1 = Node.fromString("5,3,1,null,null,null,7,6,null,null,null");
        var node2 = Node.fromString("5,3,1,null,null,null,7,null,null");
        Assertions.assertThat(node1.equals(node2)).isFalse();
    }

    @Test
    void findMin_1() {
        var node = Node.fromString("0,null,null");
        var n = node.findMin();
        Assertions.assertThat(n.getKey()).isEqualTo("0");
    }

    @Test
    void findMin_2() {
        var node = Node.fromString("0,null,2,null,null");
        var n = node.findMin();
        Assertions.assertThat(n.getKey()).isEqualTo("0");
    }

    @Test
    void findMin_3() {
        var node = Node.fromString("5,3,1,null,null,null,7,6,null,null,null");
        var n = node.findMin();
        Assertions.assertThat(n.getKey()).isEqualTo("1");
    }

    @Test
    void findMax_1() {
        var node = Node.fromString("0,null,null");
        var n = node.findMax();
        Assertions.assertThat(n.getKey()).isEqualTo("0");
    }

    @Test
    void findMax_2() {
        var node = Node.fromString("2,1,null,null,null");
        var n = node.findMax();
        Assertions.assertThat(n.getKey()).isEqualTo("2");
    }

    @Test
    void findMax_3() {
        var node = Node.fromString("5,3,null,null,7,6,null,null,null");
        var n = node.findMax();
        Assertions.assertThat(n.getKey()).isEqualTo("7");
    }

    @Test
    void findMax_4() {
        var node = Node.fromString("5,3,1,null,null,null,7,6,null,null,null");
        var n = node.findMax();
        Assertions.assertThat(n.getKey()).isEqualTo("7");
    }

    @Test
    void removeLeft() {
        var node3 = new Node<Integer>("3", 3);
        var node1 = new Node<Integer>("1", 1);
        node3.setLeft(node1);
        node3.remove("1", null);
        Assertions.assertThat(node3.getLeft()).isNull();
        Assertions.assertThat(node3.getRight()).isNull();
    }

    @Test
    void removeRight() {
        var node3 = new Node<Integer>("3", 3);
        var node1 = new Node<Integer>("1", 1);
        node1.setRight(node3);
        node1.remove("3", null);
        Assertions.assertThat(node1.getLeft()).isNull();
        Assertions.assertThat(node1.getRight()).isNull();
    }

    @Test
    void removeWithRaiseFromRight() {
        var node = Node.fromString("5,3,1,null,null,4,null,null,7,null,9,null,null");
        node.remove("3", null);
        var expected = Node.fromString("5,4,1,null,null,null,7,null,9,null,null");
        Assertions.assertThat(node.equals(expected)).isTrue();
    }

    @Test
    void removeWithRaiseFromLeft() {
        var node = Node.fromString("5,3,1,null,null,4,null,null,7,6,null,null,9,null,null");
        node.remove("7", null);
        var expected = Node.fromString("5,3,1,null,null,4,null,null,9,6,null,null,null");
        Assertions.assertThat(node.equals(expected)).isTrue();
    }

    @Test
    void removeWithRotateRight() {
        var node = Node.fromString("5,3,1,null,null,4,null,null,7,null,null");
        node.remove("7", null);
        var expected = Node.fromString("3,1,null,null,5,4,null,null,null");
        Assertions.assertThat(node.equals(expected)).isTrue();
    }

    @Test
    void removeWithRotateLeft() {
        var node = Node.fromString("5,3,null,null,7,6,null,null,9,null,null");
        node.remove("3", null);
        var expected = Node.fromString("7,5,null,6,null,null,9,null,null");
        Assertions.assertThat(node.equals(expected)).isTrue();
    }
}
