package com.alexahdp.LRUCache;

import com.alexahdp.LinkedList.Node;

record ValueRecord<T>(T value, Node<UsageRecord> node) {}
