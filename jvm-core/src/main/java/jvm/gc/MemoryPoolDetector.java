package jvm.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;

/**
 * 内存池类型探测
 *
 * @author LightMingMing
 */
public class MemoryPoolDetector {

    public static void main(String[] args) {
        new MemoryPoolDetector().printMemoryPoolType();
    }

    private void printMemoryPoolType() {
        ManagementFactory.getMemoryPoolMXBeans().stream().map(MemoryPoolMXBean::getName).forEach(System.out::println);
    }
}

// JDK 1.8
/*
Code Cache
Metaspace
Compressed Class Space
PS Eden Space
PS Survivor Space
PS Old Gen
 */

/* +XX:+UseParNewGC
Code Cache
Metaspace
Compressed Class Space
Par Eden Space
Par Survivor Space
Tenured Gen
 */

/* -XX:+UseConcMarkSweepGC
Code Cache
Metaspace
Compressed Class Space
Par Eden Space
Par Survivor Space
CMS Old Gen
 */

// JDK 11
/*
CodeHeap 'non-nmethods'
Metaspace
CodeHeap 'profiled nmethods'
Compressed Class Space
G1 Eden Space
G1 Old Gen
G1 Survivor Space
CodeHeap 'non-profiled nmethods'
 */
