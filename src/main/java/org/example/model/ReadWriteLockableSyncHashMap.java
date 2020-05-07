package org.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockableSyncHashMap {

    Map<String,String> myMap = new HashMap<String, String>();

    ReadWriteLock theLock = new ReentrantReadWriteLock();
    Lock writeLock = theLock.writeLock();   //only one
    Lock readLock = theLock.readLock();     //many can

    public void put(String key, String value) {
        try {
            writeLock.lock();
            myMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public String remove(String key) {
        String result = null;
        try {
            writeLock.lock();
            result = myMap.remove(key);
        } finally {
            writeLock.unlock();
        }
        return result;
    }

    public String get(String key) {
        String result = null;
        try{
            readLock.lock();
            result = myMap.get(key);
        } finally {
            readLock.unlock();
        }
        return result;
    }

    public boolean containsKey(String key) {
        boolean result = false;
        try{
            readLock.lock();
            result = myMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
        return result;
    }
}