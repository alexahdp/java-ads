package com.alexahdp.TTLCache;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TTLCacheTest {

    @Test
    void create() {
        try (var cache = new TTLCache<Integer>()) {
            int a = 20;
            String b = "30";
            String c = a + b;
            System.out.println(c);
            Assertions.assertThat(cache.getSize()).isEqualTo(0);
        }
    }

    @Test
    void addGet() {
        var cache = new TTLCache<Integer>();
        cache.add("1", 1);
        int val = cache.get("1");
        Assertions.assertThat(cache.getSize()).isEqualTo(1);
        Assertions.assertThat(val).isEqualTo(1);
        cache.close();
    }

    @Test
    void addRemove() {
        var cache = new TTLCache<Integer>();
        cache.add("1", 1);
        cache.remove("1");

        Assertions.assertThat(cache.getSize()).isEqualTo(0);
        Assertions.assertThat(cache.get("1")).isNull();
        cache.close();
    }

    @SneakyThrows
    @Test
    void addAndCleanByTTL() {
        var cache = new TTLCache<Integer>();
        cache.add("1", 1, 3000);
        Thread.sleep(5000);

        Assertions.assertThat(cache.getSize()).isEqualTo(0);
        Assertions.assertThat(cache.get("1")).isNull();
        cache.close();
    }

    @Test
    void addAndCleanByCapacity() {
        var cache = new TTLCache<Integer>(3);
        cache.add("1", 1);
        cache.add("2", 2);
        cache.add("3", 3);
        cache.add("4", 4);

        Assertions.assertThat(cache.getSize()).isEqualTo(3);
        Assertions.assertThat(cache.get("1")).isNull();
        Assertions.assertThat(cache.get("2")).isEqualTo(2);
        Assertions.assertThat(cache.get("3")).isEqualTo(3);
        Assertions.assertThat(cache.get("4")).isEqualTo(4);
        cache.close();
    }

    @SneakyThrows
    @Test
    void addAndCleanByCapacityAndTTL() {
        var cache = new TTLCache<Integer>(3);
        cache.add("1", 1, 5000);
        cache.add("2", 2, 5000);
        cache.add("3", 3, 5000);
        cache.add("4", 4, 1000);

        Thread.sleep(3000);

        Assertions.assertThat(cache.getSize()).isEqualTo(2);
        Assertions.assertThat(cache.get("1")).isNull();
        Assertions.assertThat(cache.get("2")).isEqualTo(2);
        Assertions.assertThat(cache.get("3")).isEqualTo(3);
        Assertions.assertThat(cache.get("4")).isNull();
        cache.close();
    }

    @SneakyThrows
    @Test
    void readWriteFromTwoThreads() {
        var cache = new TTLCache<Integer>(3);
        var thread1 = new Thread(() -> {
            cache.add("1", 1, 5000);
            cache.add("2", 2, 5000);
            cache.add("3", 3, 5000);
            cache.add("4", 4, 1000);
        });
        var thread2 = new Thread(() -> {
            cache.add("1", 1, 5000);
            cache.add("2", 2, 5000);
            cache.add("3", 3, 5000);
            cache.add("4", 4, 1000);
        });
        thread1.start();
        thread2.start();
        Thread.sleep(3000);
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(cache.getSize()).isEqualTo(2);
        Assertions.assertThat(cache.get("1")).isNull();
        Assertions.assertThat(cache.get("2")).isEqualTo(2);
        Assertions.assertThat(cache.get("3")).isEqualTo(3);
        Assertions.assertThat(cache.get("4")).isNull();
        cache.close();
    }

    // TODO: Add more tests, especially for concurrency
}
