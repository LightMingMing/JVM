package com.jvm.bytecode;

/**
 * Hot Swap Class Loader
 *
 * @author LightMingMing
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class<?> findClass(byte[] clazzBytes) {
        return defineClass(null, clazzBytes, 0, clazzBytes.length);
    }
}
