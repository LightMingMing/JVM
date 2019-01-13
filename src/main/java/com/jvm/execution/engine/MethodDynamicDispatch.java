package com.jvm.execution.engine;

/**
 * Method Dynamic Dispatch
 * <pre>
 *    public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=3, args_size=1
 *          0: new           #2                  // class com/jvm/execution/engine/MethodDynamicDispatch$MacComputer
 *          3: dup
 *          4: invokespecial #3                  // Method com/jvm/execution/engine/MethodDynamicDispatch$MacComputer."<init>":()V
 *          7: astore_1
 *          8: new           #4                  // class com/jvm/execution/engine/MethodDynamicDispatch$LenovoComputer
 *         11: dup
 *         12: invokespecial #5                  // Method com/jvm/execution/engine/MethodDynamicDispatch$LenovoComputer."<init>":()V
 *         15: astore_2
 *         16: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *         19: aload_1
 *         20: invokevirtual #7                  // Method com/jvm/execution/engine/MethodDynamicDispatch$Computer.computerType:()Ljava/lang/String;
 *         23: invokevirtual #8                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *         26: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *         29: aload_2
 *         30: invokevirtual #7                  // Method com/jvm/execution/engine/MethodDynamicDispatch$Computer.computerType:()Ljava/lang/String;
 *         33: invokevirtual #8                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *         36: return
 * </pre>
 *
 * @author LightMingMing
 */
public class MethodDynamicDispatch {
    public static void main(String[] args) {
        Computer c1 = new MacComputer();
        Computer c2 = new LenovoComputer();
        System.out.println(c1.computerType());
        System.out.println(c2.computerType());
    }

    static abstract class Computer {
        abstract String computerType();
    }

    static class MacComputer extends Computer {
        @Override
        String computerType() {
            return "Mac Computer";
        }
    }

    static class LenovoComputer extends Computer {
        @Override
        String computerType() {
            return "Lenovo Computer";
        }
    }
}
