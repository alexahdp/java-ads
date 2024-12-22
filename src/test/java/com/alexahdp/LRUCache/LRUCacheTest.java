package com.alexahdp.LRUCache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LRUCacheTest {

    @Test
    void create() {
        var cache = new LRUCache<Integer>();
        Assertions.assertThat(cache.getSize()).isEqualTo(0);
    }

    @Test
    void putGetFound() {
        var cache = new LRUCache<Integer>();
        cache.put("1", 1);
        var value = cache.get("1");
        Assertions.assertThat(value).isEqualTo(1);
        Assertions.assertThat(cache.getSize()).isEqualTo(1);
    }

    @Test
    void putGetNotFound() {
        var cache = new LRUCache<Integer>();
        cache.put("1", 1);
        var value = cache.get("0");
        Assertions.assertThat(value).isNull();
        Assertions.assertThat(cache.getSize()).isEqualTo(1);
    }

    @Test
    void cacheMaxSize() {
        var cache = new LRUCache<Integer>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        Assertions.assertThat(cache.get("1")).isEqualTo(1);
        Assertions.assertThat(cache.get("2")).isEqualTo(2);
        Assertions.assertThat(cache.get("3")).isEqualTo(3);
    }

    @Test
    void cacheMax_SizeCleaned() {
        var cache = new LRUCache<Integer>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        cache.put("4", 4);
        Assertions.assertThat(cache.get("1")).isNull();
        Assertions.assertThat(cache.get("2")).isEqualTo(2);
        Assertions.assertThat(cache.get("3")).isEqualTo(3);
        Assertions.assertThat(cache.get("4")).isEqualTo(4);
    }

    @Test
    void cacheMaxSizeCleanedAfterReorder() {
        var cache = new LRUCache<Integer>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        cache.put("4", 4);
        cache.put("1", 1);
        Assertions.assertThat(cache.get("1")).isEqualTo(1);
        Assertions.assertThat(cache.get("2")).isNull();
        Assertions.assertThat(cache.get("3")).isEqualTo(3);
        Assertions.assertThat(cache.get("4")).isEqualTo(4);
    }

    @Test
    void cacheMaxSizeCleanedAfterReorder2() {
        var cache = new LRUCache<Integer>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        cache.put("4", 4);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("4", 4);
        Assertions.assertThat(cache.get("1")).isEqualTo(1);
        Assertions.assertThat(cache.get("2")).isEqualTo(2);
        Assertions.assertThat(cache.get("3")).isNull();
        Assertions.assertThat(cache.get("4")).isEqualTo(4);
    }
}
