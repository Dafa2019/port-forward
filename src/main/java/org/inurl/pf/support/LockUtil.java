package org.inurl.pf.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

class LockUtil {

    private static volatile Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    static synchronized void lock(String name) {
        ReentrantLock reentrantLock = lockMap.get(name);
        if (reentrantLock == null) {
            reentrantLock = new ReentrantLock();
            lockMap.put(name, reentrantLock);
            reentrantLock.lock();
        } else {
            reentrantLock.lock();
        }
    }

    static synchronized void release(String name) {
        lockMap.get(name).unlock();
    }

}
