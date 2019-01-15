package com.jvm.execution.engine.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * Invoke grandFather's echo method
 *
 * @author LightMingMing
 */
public class InvokeGrandFatherMethod {

    public static void main(String[] args) {
        new Son().echo();
    }

    static class GrandFather {

        void echo() {
            System.out.println("Hello, I'm GrandFather.");
        }
    }

    static class Father extends GrandFather {
        // @Override
        void echo() {
            System.out.println("Hello, I'm Father.");
        }
    }

    static class Son extends Father {
        // @Override
        void echo() {
            // Invoke grandFather's echo method
            try {
                MethodType methodType = MethodType.methodType(void.class);
                // JDK 1.7
                MethodHandle methodHandle = MethodHandles.lookup().findSpecial(GrandFather.class, "echo", methodType, Son.class);
                methodHandle.invoke(this);

                // JDK 1.8 +
                Field IMPL_LOOKUP = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                IMPL_LOOKUP.setAccessible(true);
                MethodHandles.Lookup lookup = (MethodHandles.Lookup) IMPL_LOOKUP.get(null);
                MethodHandle methodHandle2 = lookup.findSpecial(GrandFather.class, "echo", methodType, GrandFather.class);
                methodHandle2.invoke(this);

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}

