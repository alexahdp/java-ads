package com.alexahdp.TTLCache;

public record CacheItem<T> (
    String key,
    T value,
    QueueItem queueItem
) {}
