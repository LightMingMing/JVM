package jvm.execution.engine.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * MethodHandle
 *
 * @author LightMingMing
 */
public class MethodHandleTest {

    public static void main(String[] args) throws Throwable {
        printlnMethodHandle(new ClassA()).invokeExact();
        printlnMethodHandle(new ClassB()).invokeExact();
    }

    private static MethodHandle printlnMethodHandle(Object receiver) throws Throwable {
        MethodType methodType = MethodType.methodType(void.class);
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
    }

    static class ClassA {
        public void println() {
            System.out.println("I am A");
        }
    }

    static class ClassB {
        public void println() {
            System.out.println("I am B");
        }
    }
}
