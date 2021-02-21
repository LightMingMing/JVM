package jvm.jol;

import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @see java.util.concurrent.ConcurrentHashMap
 */
@SuppressWarnings("SameParameterValue")
public class AccessArrayByUnsafe {

    // require class loader is bootstrap class loader
    // private static final Unsafe U = Unsafe.getUnsafe();

    private static final Unsafe U;
    private static final int BASE;
    private static final int SCALE;
    private static final int SHIFT;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            U = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        BASE = U.arrayBaseOffset(int[].class); // 数组头偏移 16字节
        SCALE = U.arrayIndexScale(int[].class); // 一个元素 占 4字节, scale是2的次方数
        SHIFT = 31 - Integer.numberOfLeadingZeros(SCALE); // 2^shift=scale
    }

    private static int get(int[] table, int idx) {
        return U.getIntVolatile(table, BASE + ((long)idx << SHIFT));
    }

    private static boolean compareAndSet(int[] table, int idx, int expected, int value) {
        return U.compareAndSwapInt(table, BASE + ((long)idx << SHIFT), expected, value);
    }


    public static void main(String[] args) {
        System.out.println("Array instance: base=" + BASE + ", indexScale=" + SCALE + ", shift=" + SHIFT);
        int[] table = new int[16];
        table[10] = 100;
        System.out.println(get(table, 10));

        System.out.println(compareAndSet(table, 10, 100, 200));

        System.out.println(get(table, 10));

        System.out.println(ClassLayout.parseInstance(table).toPrintable());
    }

}

/*
Array instance: base=16, indexScale=4, shift=2
100
true
200
[I object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           6d 01 00 f8 (01101101 00000001 00000000 11111000) (-134217363)
     12     4        (object header)                           10 00 00 00 (00010000 00000000 00000000 00000000) (16)
     16    64    int [I.<elements>
 */