package jvm.oom;

/**
 * 创建线程导致内存溢出
 * VM Args: -Xss2m -Xms10m -Xmx10m
 *
 * @author LightMingMing
 */
public class StackOOM {

    private static void dontStop() {
        // 书上案例的话, 只有while(true){}, 会使CPU飙到最高, 电脑直接卡死.
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
            double i = Math.PI;
            while (i < 1000000.05) {
                i += Math.PI;
            }
        }
    }

    public static void main(String[] args) {
        new StackOOM().stackLeakByCreateThread();
    }

    public void stackLeakByCreateThread() {
        while (true) {
            try {
                new Thread(StackOOM::dontStop).start();
            } catch (Error e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
/*
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at jvm.oom.StackOOM.stackLeakByCreateThread(StackOOM.java:32)
	at jvm.oom.StackOOM.main(StackOOM.java:26)
 */