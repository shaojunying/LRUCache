package com.shao.impl;

import com.shao.Cache;

import java.util.HashMap;

public class LRUCache<K, V> implements Cache<K, V> {

    public static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        Node<K, V> pre;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final HashMap<K, Node<K, V>> map = new HashMap<>();
    private final Node<K, V> head = new Node<>(null, null);
    private final Node<K, V> tail = new Node<>(null, null);
    private int size = 0;


    public LRUCache(int capacity){
        this.capacity = Math.max(1, capacity);
        head.next = tail;
        tail.pre = head;
    }

    @Override
    public void put(K key, V value) {
        Node<K, V> node;
        if ((node = map.get(key)) != null) {
            // 元素存在
            if (node.pre == head) {
                // key就是第一个元素，不用移动
                node.value = value;
                return;
            }
            // 删除元素
            Node<K, V> pre = node.pre;
            Node<K, V> next = node.next;
            pre.next = next;
            next.pre = pre;
            node.value = value;
        }
        if (node == null) {
            size ++;
            node = new Node<K, V>(key, value);
            map.put(key, node);
        }
        // 将node添加到头部
        insertNode(node, head);

        resize();
    }

    @Override
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        if (node.pre == head) {
            // key就是第一个元素，不用移动
            return node.value;
        }
        // 删除元素
        deleteNode(node);
        // 将node添加到头部
        insertNode(node, head);
        return node.value;
    }


    private void resize() {
        if (size <= capacity) {
            // 不需要删除元素
            return;
        }
        // 删除链表中最后一个元素
        Node<K, V> toBeRemovedNode = tail.pre;
        map.remove(toBeRemovedNode.key);
        deleteNode(toBeRemovedNode);
        size --;
    }

    private void deleteNode(Node<K, V> node) {
        Node<K, V> pre = node.pre;
        Node<K, V> next = node.next;
        // 移除指向node的引用
        pre.next = next;
        next.pre = pre;

        // 便于gc
        node.next = null;
        node.pre = null;
    }

    /**
     * 将node添加到pivot之后
     */
    private void insertNode(Node<K, V> node, Node<K, V> pivot) {
        Node<K, V> next = pivot.next;

        pivot.next = node;
        node.pre = pivot;

        next.pre = node;
        node.next = next;
    }

    @Override
    public void remove(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return;
        }
        // 删除节点
        deleteNode(node);
        map.remove(key);
        size --;
    }
}
