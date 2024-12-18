package com.alexahdp.LinkedList;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListTest {

    @Test
    void createEmptyLinkedList() {
        LinkedList l = new LinkedList();
        Assertions.assertThat(l.size()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void createListWith1Element() {
        LinkedList<Integer> l = new LinkedList<Integer>();
        l.add(1);
        Assertions.assertThat(l.size()).isEqualTo(1);
        Assertions.assertThat(l.get(0)).isEqualTo(1);
    }

    @Test
    @SneakyThrows
    void listWith2Elements() {
        LinkedList<Integer> l = new LinkedList<Integer>();
        l.add(1);
        l.add(2);
        Assertions.assertThat(l.size()).isEqualTo(2);
        Assertions.assertThat(l.get(0)).isEqualTo(1);
        Assertions.assertThat(l.get(1)).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    void listDeleteFirstElement() {
        LinkedList<Integer> l = new LinkedList<Integer>();
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
        LinkedList<Integer> l = new LinkedList<Integer>();
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
        LinkedList<Integer> l = new LinkedList<Integer>();
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
}
