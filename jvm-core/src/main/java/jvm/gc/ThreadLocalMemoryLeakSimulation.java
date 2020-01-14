package jvm.gc;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模拟ThreadLocal内存泄露
 *
 * @author LightMingMing
 */
public class ThreadLocalMemoryLeakSimulation {

    public static void main(String[] args) {
        // like ThreadLocalMap
        Map<WeakReference<LikeThreadLocal>, Value> map = new LinkedHashMap<>(16);
        for (int i = 0; i < 16; i++) {
            LikeThreadLocal key = new LikeThreadLocal(i);
            map.put(new WeakReference<>(key), new Value(i));
        }
        System.gc();
        map.forEach((key, value) -> System.out.println("key = " + key.get() + ", value = " + value));
    }

    static class LikeThreadLocal {
        final Integer id;

        public LikeThreadLocal(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "" + id;
        }
    }

    static class Value {
        final Integer id;

        public Value(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "" + id;
        }
    }
}

/*
key = null, value = 0
key = null, value = 1
key = null, value = 2
key = null, value = 3
key = null, value = 4
key = null, value = 5
key = null, value = 6
key = null, value = 7
key = null, value = 8
key = null, value = 9
key = null, value = 10
key = null, value = 11
key = null, value = 12
key = null, value = 13
key = null, value = 14
key = null, value = 15
 */