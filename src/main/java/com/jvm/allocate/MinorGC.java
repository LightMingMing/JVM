package com.jvm.allocate;

/**
 * MinorGC 新生代垃圾收集动作
 * VM params : -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author LightMingMing
 */
public class MinorGC {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        byte[] alloc1, alloc2, alloc3, alloc4;
        alloc1 = new byte[2 * _1M];
        alloc2 = new byte[2 * _1M];
        alloc3 = new byte[2 * _1M];
        alloc4 = new byte[4 * _1M]; // Minor GC
    }
}

/*
JDK 8
[GC (Allocation Failure) [PSYoungGen: 8192K->624K(9216K)] 12288K->4728K(19456K), 0.0009462 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
Heap
 PSYoungGen      total 9216K, used 872K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 8192K, 3% used [0x00000007bf600000,0x00000007bf63e0d0,0x00000007bfe00000)
  from space 1024K, 60% used [0x00000007bfe00000,0x00000007bfe9c010,0x00000007bff00000)
  to   space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
 ParOldGen       total 10240K, used 4104K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  object space 10240K, 40% used [0x00000007bec00000,0x00000007bf002010,0x00000007bf600000)
 Metaspace       used 3277K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 360K, capacity 388K, committed 512K, reserved 1048576K
 */