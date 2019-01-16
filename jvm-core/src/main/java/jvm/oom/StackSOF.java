package jvm.oom;

/**
 * 虚拟机栈和本地方法栈OOM
 * VM Args: -Xss128k
 * VM Args: -Xss228k
 * VM Args: -Xss256k
 * @author LightMingMing
 */
public class StackSOF {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        StackSOF stackSOF = new StackSOF();
        try {
            stackSOF.stackLeak();
        } catch (Throwable e) {
            System.out.println("The stack Length is :" + stackSOF.stackLength);
            throw e;
        }
    }
}
/*
VM Args: -Xss128k
The stack size specified is too small, Specify at least 228k
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.

VM Args: -Xss228k
Exception in thread "main" java.lang.StackOverflowError
The stack Length is :1516
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:14)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	...

VM Args: -Xss256k
The stack Length is :1841
Exception in thread "main" java.lang.StackOverflowError
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:14)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	at com.jvm.oom.StackSOF.stackLeak(StackSOF.java:15)
	...
 */
