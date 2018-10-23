package org.inurl.pf.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

class Lock {

    private static Map<String, CountDownLatch> latchMap = new ConcurrentHashMap<>();

    static class Connection {

        private static final String prefix = "connection-";

        static void init(String address) {
            CountDownLatch lock = new CountDownLatch(1);
            latchMap.put(prefix + address, lock);
        }

        static void release(String address) {
            latchMap.get(prefix + address).countDown();
        }

        static void wait(String address) {
            CountDownLatch latch = latchMap.get(prefix + address);
            if (latch != null) {
                try {
                    latch.await();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }



}
