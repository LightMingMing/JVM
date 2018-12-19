package com.jvm.gc;

/**
 * 对象回收会进行多次标记，对对象进行可达性分析后，发现没有引用链，将会第一次标记，并进行筛选(是否有必要执行finalize方法)
 * 如果有必要执行finalize方法，将会把对象放在一个F-Queue队列中，建立Finalizer线程去执行finalize方法
 * 在进行第二次标记，发现有重新被引用，将被从"即将回收"的集合中移出
 * 从JDK 9, finalize已被标注为废弃方法
 * <pre>
 *     @code @Deprecated(since="9")
 *     protected void finalize() throws Throwable { }
 * </pre>
 *
 * @author LightMingMing
 */
public class FinalizeEscapeGC {

    private static FinalizeEscapeGC SAVE_HOKE = null;

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOKE = new FinalizeEscapeGC();
        System.out.println(Thread.currentThread());
        execute();
        execute();
    }

    private static void execute() throws InterruptedException {
        SAVE_HOKE = null;
        System.gc();
        Thread.sleep(1000);
        if (SAVE_HOKE != null) {
            System.out.println("Hi, I'm still alive...");
        } else {
            System.out.println("I'm dead...");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Execute the finalize method...");
        System.out.println(Thread.currentThread()); // Finalizer 线程
        super.finalize();
        SAVE_HOKE = this; // 重新引用
    }

}

/*
JDK 8
Thread[main,5,main]
Execute the finalize method...
Thread[Finalizer,8,system]
Hi, I'm still alive...
I'm dead...
 */

/*
JDK 11
Thread[main,5,main]
Execute the finalize method...
Thread[Finalizer,8,system]
Hi, I'm still alive...
I'm dead...
 */
