package com.alexahdp.TTLCache;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TTLCache<T> implements AutoCloseable {
    private static final int DEFAULT_TTL = 1000;
    private static final int DEFAULT_PERIOD = 1000;

    private final int period = DEFAULT_PERIOD;
    private int capacity;
    private Long size = 0L;
    private Thread cleaner;

    private final Map<String, CacheItem<T>> cache = new HashMap<String, CacheItem<T>>();
    private PriorityQueue<QueueItem<T>> queue;

    public TTLCache(int capacity) {
        this.capacity = capacity;
        this.queue = new PriorityQueue<QueueItem<T>>(this.capacity);
        this.cleaner = new Thread(this::clean);
        this.cleaner.start();
    }

    public TTLCache() {
        this.capacity = 10;
        this.queue = new PriorityQueue<QueueItem<T>>(this.capacity);
        this.cleaner = new Thread(this::clean);
        this.cleaner.start();
    }

    public void add(String key, T value) {
        this.add(key, value, DEFAULT_TTL);
    }

    public synchronized void close() {
        this.cleaner.interrupt();
    }

    public synchronized void add(String key, T value, int ttl) {
        if (this.size >= this.capacity) {
            // remove the oldest item
            var item = this.queue.poll();
            this.cache.remove(item.key());
            this.size -= 1;
        }

        if (this.cache.containsKey(key)) {
            var cacheItem = this.cache.get(key);
            this.queue.remove(cacheItem.queueItem());
            var queueItem = new QueueItem<T>(key, System.currentTimeMillis() + ttl);
            this.queue.add(queueItem);
        } else {
            var queueItem = new QueueItem<T>(key, System.currentTimeMillis() + ttl);
            this.cache.put(key, new CacheItem<>(key, value, queueItem));
            this.queue.add(queueItem);
            this.size += 1;
        }
    }

    public synchronized T get(String key) {
        var item = this.cache.getOrDefault(key, null);
        if (item != null) {
            return item.value();
        } else {
            return null;
        }
    }

    public synchronized void remove(String key) {
        this.cache.remove(key);
        this.size -= 1;
    }

    public synchronized long getSize() {
        return this.size;
    }

    private void clean() {
        while (true) {
            try {
                Thread.sleep(this.period);
                // take lock on this
                synchronized(this) {
                    while (
                        this.queue.size() > 0 && this.queue.peek().ttl() <= System.currentTimeMillis()
                    ) {
                        QueueItem<T> item = this.queue.poll();
                        this.cache.remove(item.key());
                        this.size -= 1;
                    }
                }
            } catch (InterruptedException e) {
                // do nothing, it's time to stop
            }
        }
    }
}
