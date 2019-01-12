package com.jvm.allocate;

/**
 * 长期存活对象进入老年代
 * <p>
 * VM params: -verbose:gc -XX:+UseConcMarkSweepGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * </p>
 * <pre>
 *      byte[] allocation1, allocation2, allocation3;
 *      allocation1 = new byte[_1M / 4];
 *      allocation2 = new byte[4 * _1M];
 *      allocation3 = new byte[4 * _1M]; // Minor GC
 * [GC (Allocation Failure) [ParNew
 * Desired survivor size 524288 bytes, new threshold 1 (max 1)
 * - age   1:     700384 bytes,     700384 total
 * : 6290K->736K(9216K), 0.0066588 secs] 6290K->4834K(19456K), 0.0067270 secs] [Times: user=0.04 sys=0.00, real=0.00 secs]
 * Heap
 * par new generation   total 9216K, used 4996K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
 * eden space 8192K,  52% used [0x00000007bec00000, 0x00000007bf0290f0, 0x00000007bf400000)
 * from space 1024K,  71% used [0x00000007bf500000, 0x00000007bf5b81f8, 0x00000007bf600000)
 * to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
 * concurrent mark-sweep generation total 10240K, used 4098K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
 * Metaspace       used 3171K, capacity 4496K, committed 4864K, reserved 1056768K
 * class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
 * </pre>
 * <pre>
 *     byte[] allocation1, allocation2, allocation3;
 *     allocation1 = new byte[_1M / 4];
 *     allocation2 = new byte[4 * _1M];
 *     allocation3 = new byte[4 * _1M]; // Minor GC
 *     allocation3 = null;
 *     allocation3 = new byte[4 * _1M]; // Minor GC
 *
 * [GC (Allocation Failure) [ParNew
 * Desired survivor size 524288 bytes, new threshold 1 (max 1)
 * - age   1:     702136 bytes,     702136 total
 * : 6290K->728K(9216K), 0.0034787 secs] 6290K->4826K(19456K), 0.0035238 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
 * [GC (Allocation Failure) [ParNew
 * Desired survivor size 524288 bytes, new threshold 1 (max 1)
 * - age   1:        344 bytes,        344 total
 * : 4906K->142K(9216K), 0.0051971 secs] 9004K->4933K(19456K), 0.0052307 secs] [Times: user=0.02 sys=0.01, real=0.00 secs]
 * Heap
 * par new generation   total 9216K, used 4376K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
 * eden space 8192K,  51% used [0x00000007bec00000, 0x00000007bf022530, 0x00000007bf400000)
 * from space 1024K,  13% used [0x00000007bf400000, 0x00000007bf423ae0, 0x00000007bf500000)
 * to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
 * concurrent mark-sweep generation total 10240K, used 4790K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
 * Metaspace       used 3195K, capacity 4496K, committed 4864K, reserved 1056768K
 * class space    used 354K, capacity 388K, committed 512K, reserved 1048576K
 * </pre>
 * <pre>
 *      byte[] allocation1, allocation2, allocation3, allocation4;
 *      allocation1 = new byte[_1M / 4];
 *      allocation2 = new byte[4 * _1M];
 *      allocation3 = new byte[4 * _1M]; // Minor GC
 *      allocation4 = new byte[4 * _1M]; // Minor GC + Full GC(Major GC)
 *
 * Desired survivor size 524288 bytes, new threshold 1 (max 1)
 * - age   1:     702008 bytes,     702008 total
 * : 6290K->744K(9216K), 0.0033418 secs] 6290K->4842K(19456K), 0.0033825 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [ParNew
 * Desired survivor size 524288 bytes, new threshold 1 (max 1)
 * - age   1:        400 bytes,        400 total
 * : 4922K->139K(9216K), 0.0041074 secs] 9020K->9026K(19456K), 0.0041293 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
 * [GC (CMS Initial Mark) [1 CMS-initial-mark: 8886K(10240K)] 13177K(19456K), 0.0003415 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [CMS-concurrent-mark-start]
 * Heap
 * par new generation   total 9216K, used 4372K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
 * eden space 8192K,  51% used [0x00000007bec00000, 0x00000007bf022530, 0x00000007bf400000)
 * from space 1024K,  13% used [0x00000007bf400000, 0x00000007bf422e20, 0x00000007bf500000)
 * to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
 * concurrent mark-sweep generation total 10240K, used 8886K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
 * Metaspace       used 3196K, capacity 4496K, committed 4864K, reserved 1056768K
 * class space    used 354K, capacity 388K, committed 512K, reserved 1048576K
 * </pre>
 * <p>
 * VM params: -verbose:gc -XX:+UseConcMarkSweepGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=3 -XX:+PrintTenuringDistribution
 * </p>
 *
 * @author LightMingMing
 * @since 1.8
 */
public class LongTermSurvivalIntoTenured {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1M / 4];
        allocation2 = new byte[4 * _1M];
        allocation3 = new byte[4 * _1M]; // Minor GC
        allocation3 = null;
        allocation3 = new byte[4 * _1M]; // Minor GC

    }
}