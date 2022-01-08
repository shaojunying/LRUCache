package com.shao.impl;

import com.shao.Cache;

public class LRUCache<T> implements Cache<T> {
    @Override
    public void put(String key, Object value) {
        System.out.println("put");
    }

    @Override
    public T get(String key) {
        System.out.println("get");
        return null;
    }

    @Override
    public void remove(String key) {
        System.out.println("remove");
    }
}
