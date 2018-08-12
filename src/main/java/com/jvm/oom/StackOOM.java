package com.jvm.oom;

/**
 * 创建线程导致内存溢出
 * VM Args: -Xss2m
 *
 * @author LightMingMing
 */
public class StackOOM {

    private static void dontStop() {
        while (true) {
        }
    }

    public void stackLeakByCreateThread() {
        while (true) {
            new Thread(StackOOM::dontStop).start();
        }
    }

    public static void main(String[] args) {
        new StackOOM().stackLeakByCreateThread();
    }
}
/*
难受，运行不出来，电脑直接卡死...
 */