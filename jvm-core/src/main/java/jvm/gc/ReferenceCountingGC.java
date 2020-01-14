package jvm.gc;

/**
 * GC
 * VM params : JDK 1.8 -XX:+PrintGC -XX:+PrintGCDetails JDK 11 -Xlog:gc -Xlog:gc*
 *
 * @author LightMingMing
 * @see <a href='https://chriswhocodes.com'>hotspot options jdk</a>
 */
@SuppressWarnings({"unused", "UnusedAssignment"})
public class ReferenceCountingGC {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC a = new ReferenceCountingGC();
        ReferenceCountingGC b = new ReferenceCountingGC();

        a.instance = b;
        b.instance = a;

        a = null;
        b = null;

        System.gc();
    }

}

/*
JDK 1.8
java -XX:+PrintGC -XX:+PrintGCDetails com/jvm/gc/ReferenceCounting
[GC (System.gc()) [PSYoungGen: 9339K->592K(76288K)] 9339K->600K(251392K), 0.0008198 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
[Full GC (System.gc()) [PSYoungGen: 592K->0K(76288K)] [ParOldGen: 8K->429K(175104K)] 600K->429K(251392K), [Metaspace: 3166K->3166K(1056768K)], 0.0037628 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
Heap
 PSYoungGen      total 76288K, used 655K [0x000000076ab00000, 0x0000000770000000, 0x00000007c0000000)
  eden space 65536K, 1% used [0x000000076ab00000,0x000000076aba3ee8,0x000000076eb00000)
  from space 10752K, 0% used [0x000000076eb00000,0x000000076eb00000,0x000000076f580000)
  to   space 10752K, 0% used [0x000000076f580000,0x000000076f580000,0x0000000770000000)
 ParOldGen       total 175104K, used 429K [0x00000006c0000000, 0x00000006cab00000, 0x000000076ab00000)
  object space 175104K, 0% used [0x00000006c0000000,0x00000006c006b5c8,0x00000006cab00000)
 Metaspace       used 3173K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
 */

/*
JDK 11
java -Xlog:gc com/jvm/gc/ReferenceCountingGC
[0.010s][info][gc] Using G1
[0.085s][info][gc] GC(0) Pause Full (System.gc()) 7M->0M(8M) 2.919ms

java -Xlog:gc -Xlog:gc* com/jvm/gc/ReferenceCountingGC
[0.012s][info][gc,heap] Heap region size: 1M
[0.017s][info][gc     ] Using G1
[0.017s][info][gc,heap,coops] Heap address: 0x0000000700000000, size: 4096 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
[0.261s][info][gc,task      ] GC(0) Using 6 workers of 10 for full compaction
[0.261s][info][gc,start     ] GC(0) Pause Full (System.gc())
[0.261s][info][gc,phases,start] GC(0) Phase 1: Mark live objects
[0.262s][info][gc,stringtable ] GC(0) Cleaned string and symbol table, strings: 2609 processed, 3 removed, symbols: 26128 processed, 0 removed
[0.262s][info][gc,phases      ] GC(0) Phase 1: Mark live objects 0.794ms
[0.262s][info][gc,phases,start] GC(0) Phase 2: Prepare for compaction
[0.262s][info][gc,phases      ] GC(0) Phase 2: Prepare for compaction 0.309ms
[0.262s][info][gc,phases,start] GC(0) Phase 3: Adjust pointers
[0.262s][info][gc,phases      ] GC(0) Phase 3: Adjust pointers 0.407ms
[0.262s][info][gc,phases,start] GC(0) Phase 4: Compact heap
[0.263s][info][gc,phases      ] GC(0) Phase 4: Compact heap 0.517ms
[0.264s][info][gc,heap        ] GC(0) Eden regions: 3->0(3)
[0.264s][info][gc,heap        ] GC(0) Survivor regions: 0->0(0)
[0.264s][info][gc,heap        ] GC(0) Old regions: 0->3
[0.264s][info][gc,heap        ] GC(0) Humongous regions: 6->0
[0.264s][info][gc,metaspace   ] GC(0) Metaspace: 6318K->6318K(1056768K)
[0.264s][info][gc             ] GC(0) Pause Full (System.gc()) 8M->0M(10M) 3.787ms
[0.265s][info][gc,cpu         ] GC(0) User=0.00s Sys=0.00s Real=0.01s
[0.265s][info][gc,heap,exit   ] Heap
[0.265s][info][gc,heap,exit   ]  garbage-first heap   total 10240K, used 966K [0x0000000700000000, 0x0000000800000000)
[0.265s][info][gc,heap,exit   ]   region size 1024K, 1 young (1024K), 0 survivors (0K)
[0.265s][info][gc,heap,exit   ]  Metaspace       used 6344K, capacity 6379K, committed 6528K, reserved 1056768K
[0.265s][info][gc,heap,exit   ]   class space    used 552K, capacity 570K, committed 640K, reserved 1048576K
 */