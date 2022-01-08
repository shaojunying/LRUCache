package com.shao;

public interface Cache<T> {

    void put(String key, Object value);

    T get(String key);

    void remove(String key);

}
