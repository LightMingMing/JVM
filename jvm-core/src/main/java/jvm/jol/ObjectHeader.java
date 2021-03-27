package jvm.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 对象头信息
 *
 * @author LightMingMing
 */

// markOop.hpp
//    [JavaThread* | epoch | age | 1 | 01]       lock is biased toward given thread
//    [0           | epoch | age | 1 | 01]       lock is anonymously biased

//  - the two lock bits are used to describe three states: locked/unlocked and monitor.
//
//    [ptr             | 00]  locked             ptr points to real header on stack
//    [header      | 0 | 01]  unlocked           regular object header
//    [ptr             | 10]  monitor            inflated lock (header is wapped out)
//    [ptr             | 11]  marked             used by markSweep to mark an object
//                                               not valid at any other time
public class ObjectHeader {

    private final Object lock = new Object();

    public static void main(String[] args) {

        // TODO 不知道为什么没有出现偏向锁, 即使加上参数-XX:+UseBiasedLocking
        Consumer
        ObjectHeader obj = new ObjectHeader();

        // unlocked
        System.out.println(ClassLayout.parseInstance(obj.lock).toPrintable());

        new Thread(() -> {
            synchronized (obj.lock) {
                // locked
                System.out.println(ClassLayout.parseInstance(obj.lock).toPrintable());
                sleep(200);
                // monitor
                System.out.println(ClassLayout.parseInstance(obj.lock).toPrintable());
            }
        }).start();

        sleep(100);

        new Thread(() -> {
            synchronized (obj.lock) {
                sleep(1);
            }
        }).start();
    }

    static void sleep(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}

/*
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           08 29 aa 0e (00001000 00101001 10101010 00001110) (246032648)
      4     4        (object header)                           00 70 00 00 (00000000 01110000 00000000 00000000) (28672)

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           6a b1 02 81 (01101010 10110001 00000010 10000001) (-2130529942)
      4     4        (object header)                           ec 7f 00 00 (11101100 01111111 00000000 00000000) (32748)
 */