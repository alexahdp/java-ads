package com.alexahdp.TTLCache;

public record QueueItem<T>(
    String key,
    long ttl
) implements Comparable<QueueItem<T>> {
    @Override
    public int compareTo(QueueItem<T> that) {
        return Long.compare(this.ttl, that.ttl);
    }
}
