package jvm.jol;

/**
 * 多线程修改同一个缓存行(64字节)中的数据时, 为保证缓存一致性, 会触发缓存失效, 降低性能
 * <p> output:
 * <p>
 * GenericLongWrapper takes   672 ms to update 100000000 times when threads is 1
 * PaddedLongWrapper  takes   688 ms to update 100000000 times when threads is 1
 * GenericLongWrapper takes  4273 ms to update 100000000 times when threads is 2
 * PaddedLongWrapper  takes   637 ms to update 100000000 times when threads is 2
 * GenericLongWrapper takes  5933 ms to update 100000000 times when threads is 3
 * PaddedLongWrapper  takes   677 ms to update 100000000 times when threads is 3
 * GenericLongWrapper takes  5661 ms to update 100000000 times when threads is 4
 * PaddedLongWrapper  takes   650 ms to update 100000000 times when threads is 4
 * GenericLongWrapper takes  8938 ms to update 100000000 times when threads is 5
 * PaddedLongWrapper  takes   647 ms to update 100000000 times when threads is 5
 *
 * @author LightMingMing
 */
public class CacheLineDemo {

    public static long getUpdateTime(LongWrapper[] wrappers, int updateCount) throws InterruptedException {
        Thread[] threads = new Thread[wrappers.length];
        for (int i = 0; i < wrappers.length; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                for (int v = 0; v < updateCount; v++) {
                    wrappers[idx].setValue(v);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        long start = System.nanoTime();

        for (Thread thread : threads) {
            thread.join();
        }
        return (System.nanoTime() - start) / 1_000_000;
    }

    public static void main(String[] args) throws InterruptedException {
        final int count = 100_000_000;
        for (int i = 1; i < 6; i++) {
            LongWrapper[] genericWrappers = longWrapperArray(i, GenericLongWrapper::new);
            long updateTime = getUpdateTime(genericWrappers, count);
            System.out.printf("GenericLongWrapper takes %5d ms to update %d times when threads is %d\n", updateTime, count, i);

            LongWrapper[] paddedWrappers = longWrapperArray(i, PaddedLongWrapper::new);
            updateTime = getUpdateTime(paddedWrappers, count);
            System.out.printf("PaddedLongWrapper  takes %5d ms to update %d times when threads is %d\n", updateTime, count, i);
        }
    }

    static LongWrapper[] longWrapperArray(int size, LongWrapperFactory factory) {
        LongWrapper[] wrappers = new LongWrapper[size];
        for (int i = 0; i < size; i++) {
            wrappers[i] = factory.newLongWrapper();
        }
        return wrappers;
    }

    interface LongWrapper {
        void setValue(long value);
    }

    interface LongWrapperFactory {
        LongWrapper newLongWrapper();
    }

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    static class GenericLongWrapper implements LongWrapper {

        private volatile long value;

        @Override
        public void setValue(long value) {
            this.value = value;
        }
    }

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    static class PaddedLongWrapper implements LongWrapper {

        // left padding
        private long l1, l2, l3, l4, l5, l6, l7;

        private volatile long value;

        // right padding
        private long r1, r2, r3, r4, r5, r6, r7;

        @Override
        public void setValue(long value) {
            this.value = value;
        }
    }
}
