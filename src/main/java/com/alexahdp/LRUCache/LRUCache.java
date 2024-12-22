package com.alexahdp.LRUCache;

import com.alexahdp.LinkedList.LinkedList;
import com.alexahdp.LinkedList.Node;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<T> {
    private Long capacity;
    private Long size = 0L;
    private final Map<String, ValueRecord<T>> cache = new HashMap<String, ValueRecord<T>>();
    private final LinkedList<UsageRecord> usage = new LinkedList<UsageRecord>();

    public LRUCache() {
        this.capacity = 10L;
    }

    public LRUCache(long capacity) {
        this.capacity = capacity;
    }

    @SneakyThrows
    public void put(String key, T value) {
        if (this.cache.containsKey(key)) {
            var valueRecord = this.cache.get(key);
            this.usage.deleteNode(valueRecord.node());
            this.usage.add(valueRecord.node().getValue());
            return;
        }
        if (this.size >= this.capacity) {
            UsageRecord lastUsedRecord = this.usage.get(0);
            this.cache.remove(lastUsedRecord.key());
            this.usage.delete(0);
            this.size -= 1;
        }
        this.size += 1;
        Node<UsageRecord> usageNode = this.usage.add(new UsageRecord(key));
        this.cache.put(key, new ValueRecord<T>(value, usageNode));
    }

    public T get(String key) {
        var value = this.cache.get(key);
        if (value != null) {
            return value.value();
        }
        return null;
    }

    public long getSize() {
        return this.size;
    }
}
