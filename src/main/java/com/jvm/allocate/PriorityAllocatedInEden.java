package com.jvm.allocate;

/**
 * 对象优先在新生代(New Generation)分配
 * VM params : JDK 8 -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * @author LightMingMing
 */
public class PriorityAllocatedInEden {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        final int size = 4;
        byte[][] allocs = new byte[size][];
        for (int i = 0; i < size; i ++)
            allocs[i] = new byte[_1M];
    }
}

/*
JDK 8 -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
size = 4
Heap
 PSYoungGen      total 9216K, used 7222K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 8192K, 88% used [0x00000007bf600000,0x00000007bfd0da98,0x00000007bfe00000)
  from space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
  to   space 1024K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007bff00000)
 ParOldGen       total 10240K, used 0K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  object space 10240K, 0% used [0x00000007bec00000,0x00000007bec00000,0x00000007bf600000)
 Metaspace       used 3172K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 348K, capacity 388K, committed 512K, reserved 1048576K
 */

/*
JDK 8 -XX:+UseParNewGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
size = 4
/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java -XX:+UseParNewGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51944:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/mingming/IdeaProjects/JVM/out/production/classes:/Users/mingming/.gradle/caches/modules-2/files-2.1/cglib/cglib/3.2.8/331c90f8cd3be5b2ee225354bed68862ba0fd3c8/cglib-3.2.8.jar:/Users/mingming/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm/6.2.1/c01b6798f81b0fc2c5faa70cbe468c275d4b50c7/asm-6.2.1.jar:/Users/mingming/.gradle/caches/modules-2/files-2.1/org.apache.ant/ant/1.10.3/88becdeb77cdd2457757b7268e1a10666c03d382/ant-1.10.3.jar:/Users/mingming/.gradle/caches/modules-2/files-2.1/org.apache.ant/ant-launcher/1.10.3/9dd5189e7f561ca19833b4e3672720b9bc5cb2fe/ant-launcher-1.10.3.jar com.jvm.allocate.PriorityAllocatedInEden
Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release
Heap
 par new generation   total 9216K, used 6198K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  eden space 8192K,  75% used [0x00000007bec00000, 0x00000007bf20da88, 0x00000007bf400000)
  from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
  to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
 tenured generation   total 10240K, used 0K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
   the space 10240K,   0% used [0x00000007bf600000, 0x00000007bf600000, 0x00000007bf600200, 0x00000007c0000000)
 Metaspace       used 3172K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 348K, capacity 388K, committed 512K, reserved 1048576K
 */

/*
JDK 8 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
size = 4
Heap
 par new generation   total 9216K, used 6198K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  eden space 8192K,  75% used [0x00000007bec00000, 0x00000007bf20d9d8, 0x00000007bf400000)
  from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
  to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
 concurrent mark-sweep generation total 10240K, used 0K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 3173K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 348K, capacity 388K, committed 512K, reserved 1048576K
 */

/*
JDK 11 -Xms20M -Xmx20M -Xmn10M -Xlog:gc* -XX:SurvivorRatio=8

size = 1
[0.011s][info][gc,heap] Heap region size: 1M
[0.011s][info][gc,heap] Heap region size: 1M
[0.012s][info][gc     ] Using G1
[0.012s][info][gc,heap,coops] Heap address: 0x00000007fec00000, size: 20 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
[0.178s][info][gc,heap,exit ] Heap
[0.178s][info][gc,heap,exit ]  garbage-first heap   total 20480K, used 4096K [0x00000007fec00000, 0x0000000800000000)
[0.178s][info][gc,heap,exit ]   region size 1024K, 3 young (3072K), 0 survivors (0K)
[0.178s][info][gc,heap,exit ]  Metaspace       used 6321K, capacity 6379K, committed 6528K, reserved 1056768K
[0.178s][info][gc,heap,exit ]   class space    used 546K, capacity 570K, committed 640K, reserved 1048576K

size = 2
[0.010s][info][gc,heap] Heap region size: 1M
[0.011s][info][gc     ] Using G1
[0.011s][info][gc,heap,coops] Heap address: 0x00000007fec00000, size: 20 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
[0.189s][info][gc,heap,exit ] Heap
[0.189s][info][gc,heap,exit ]  garbage-first heap   total 20480K, used 6144K [0x00000007fec00000, 0x0000000800000000)
[0.189s][info][gc,heap,exit ]   region size 1024K, 3 young (3072K), 0 survivors (0K)
[0.189s][info][gc,heap,exit ]  Metaspace       used 6336K, capacity 6379K, committed 6528K, reserved 1056768K
[0.189s][info][gc,heap,exit ]   class space    used 548K, capacity 570K, committed 640K, reserved 1048576K
 */