package jvm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出
 * VM args: -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * @author LightMingMing
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0]; // private static final Unsafe theUnsafe;
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        for (int i = 0; i < 20; i++) {
            unsafe.allocateMemory(_1MB);
        }
    }

}

/*
沒有达到理想效果...
 */