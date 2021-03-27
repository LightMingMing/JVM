package jvm.mm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Node {

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long itemOffset;

    static {

        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);

            Class<?> k = Node.class;
            itemOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("item"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    volatile Object item;

    public static void main(String[] args) {
        Node n = new Node();
        n.putObject(1);
        n.putObjectVolatile(2);
        n.putDirect(3);
    }


    public void putObject(Object item) {
        UNSAFE.putObject(this, itemOffset, item);
    }

    public void putObjectVolatile(Object item) {
        UNSAFE.putObjectVolatile(this, itemOffset, item);
    }

    public void putDirect(Object item) {
        this.item = item;
    }
}
