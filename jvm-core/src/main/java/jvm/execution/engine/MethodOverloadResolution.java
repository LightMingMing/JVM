package jvm.execution.engine;

/**
 * MethodOverloadResolution || Static Dispatch
 * <p>javac -v MethodOverloadResolution</p>
 * <pre>
 * public static void main(java.lang.String[]);
 * descriptor: ([Ljava/lang/String;)V
 * flags: (0x0009) ACC_PUBLIC, ACC_STATIC
 * Code:
 * stack=2, locals=3, args_size=1
 * 0: new           #5                  // class MethodOverloadResolution$MacComputer
 * 3: dup
 * 4: invokespecial #6                  // Method MethodOverloadResolution$MacComputer."<init>":()V
 * 7: astore_1
 * 8: new           #7                  // class MethodOverloadResolution$LenovoComputer
 * 11: dup
 * 12: invokespecial #8                  // Method MethodOverloadResolution$LenovoComputer."<init>":()V
 * 15: astore_2
 * 16: getstatic     #9                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 19: aload_1
 * 20: invokestatic  #10                 // Method computerType:(LMethodOverloadResolution$Computer;)Ljava/lang/String;
 * 23: invokevirtual #11                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 * 26: getstatic     #9                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 29: aload_2
 * 30: invokestatic  #10                 // Method computerType:(LMethodOverloadResolution$Computer;)Ljava/lang/String;
 * 33: invokevirtual #11                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 * 36: return
 * </pre>
 *
 * @author LightMingMing
 */
public class MethodOverloadResolution {

    public static String computerType(Computer computer) {
        return "Computer";
    }

    public static String computerType(MacComputer macComputer) {
        return "Mac Computer";
    }

    public static String computerType(LenovoComputer lenovoComputer) {
        return "Lenovo Computer";
    }

    public static void main(String[] args) {
        Computer mac = new MacComputer();
        Computer lenovo = new LenovoComputer();
        System.out.println(computerType(mac));
        System.out.println(computerType(lenovo));
    }

    static abstract class Computer {
    }

    static class MacComputer extends Computer {
    }

    static class LenovoComputer extends Computer {
    }

}

/* output
Computer
Computer
 */