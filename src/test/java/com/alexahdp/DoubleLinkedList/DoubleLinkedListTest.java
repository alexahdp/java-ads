package com.alexahdp.DoubleLinkedList;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleLinkedListTest {

    @Test
    void createEmptyLinkedList() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();
        Assertions.assertThat(l.size()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void createListWith1Element() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();
        l.add(1);
        Assertions.assertThat(l.size()).isEqualTo(1);
        Assertions.assertThat(l.get(0)).isEqualTo(1);
    }

    @Test
    @SneakyThrows
    void listWith2Elements() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();
        l.add(1);
        l.add(2);
        Assertions.assertThat(l.size()).isEqualTo(2);
        Assertions.assertThat(l.get(0)).isEqualTo(1);
        Assertions.assertThat(l.get(1)).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    void listDeleteFirstElement() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();
        l.add(1);
        l.add(2);
        boolean result = l.delete(0);
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(l.size()).isEqualTo(1);
        Assertions.assertThat(l.get(0)).isEqualTo(2);
        Assertions.assertThatThrownBy(() -> {
            l.get(1);
        }).isInstanceOf(Exception.class);
    }

    @Test
    @SneakyThrows
    void listDeleteLastElement() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        boolean result = l.delete(2);
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(l.size()).isEqualTo(2);
        Assertions.assertThat(l.get(0)).isEqualTo(1);
        Assertions.assertThat(l.get(1)).isEqualTo(2);
        Assertions.assertThatThrownBy(() -> {
            l.get(3);
        }).isInstanceOf(Exception.class);
    }

    @Test
    @SneakyThrows
    void listDeleteMiddleElement() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        boolean result = l.delete(1);
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(l.size()).isEqualTo(2);
        Assertions.assertThat(l.get(0)).isEqualTo(1);
        Assertions.assertThat(l.get(1)).isEqualTo(3);
        Assertions.assertThatThrownBy(() -> {
            l.get(2);
        }).isInstanceOf(Exception.class);
    }

    @Test
    void toString_1() {
        var l = new DoubleLinkedList<>();
        Assertions.assertThat(l.toString()).isEqualTo("");
    }

    @Test
    void toString_2() {
        var l = new DoubleLinkedList<>();
        l.add(1);
        Assertions.assertThat(l.toString()).isEqualTo("1");
    }

    @Test
    void toString_3() {
        var l = new DoubleLinkedList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        Assertions.assertThat(l.toString()).isEqualTo("1,2,3");
    }

    @Test
    @SneakyThrows
    void findAndDelete_1() {
        var l = new DoubleLinkedList<Integer>();
        l.add(1);
        l.findAndDelete(1);
        Assertions.assertThat(l.toString()).isEqualTo("");
        validate(getHead(l));
    }

    @Test
    @SneakyThrows
    void findAndDelete_2() {
        var l = new DoubleLinkedList<>();
        l.add(1);
        l.add(2);
        l.findAndDelete(1);
        Assertions.assertThat(l.toString()).isEqualTo("2");
        validate(getHead(l));
    }

    @Test
    @SneakyThrows
    void findAndDelete_3() {
        var l = new DoubleLinkedList<>();
        l.add(1);
        l.add(2);
        l.findAndDelete(2);
        Assertions.assertThat(l.toString()).isEqualTo("1");
        validate(getHead(l));
    }

    @Test
    @SneakyThrows
    void findAndDelete_4() {
        var l = new DoubleLinkedList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.findAndDelete(2);
        Assertions.assertThat(l.toString()).isEqualTo("1,3");
        validate(getHead(l));
    }

    @Test
    @SneakyThrows
    void findAndDelete_5() {
        var l = new DoubleLinkedList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.findAndDelete(2);
        Assertions.assertThat(l.toString()).isEqualTo("1,3");
        validate(getHead(l));
    }

    // ====== helpers =======

    private <T> void validate(Node<T> list) throws Exception {
        Node<T> current = list;
        Node<T> prev = null;
        while (current != null) {
            if (current.getPrev() != prev) {
                throw new Exception("Incorrect links");
            }
            prev = current;
            current = current.getNext();
        }
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private <T> Node<T> getHead(DoubleLinkedList<T> l) {
        java.lang.reflect.Field field = DoubleLinkedList.class.getDeclaredField("head");
        field.setAccessible(true);

        return (Node<T>) field.get(l);
    }
}
