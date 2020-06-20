package jvm.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;

/**
 * 垃圾收集器探测
 * <p>
 * JDK 1.8 可能组合
 * <li>PS Scavenge + PS MarkSweep</li>
 * <li>ParNew + ConcurrentMarkSweep</li>
 * <li>G1 Young Generation + G1 Old Generation</li>
 * <li>ParNew + MarkSweepCompact (不推荐)</li>
 * <li>Copy + MarkSweepCompact (不推荐)</li>
 *
 * @author LightMingMing
 */
public class GarbageCollectorTypeDetector {

    public static void main(String[] args) {
        new GarbageCollectorTypeDetector().printGarbageCollectorType();
    }

    private void printGarbageCollectorType() {
        ManagementFactory.getGarbageCollectorMXBeans().stream().map(MemoryManagerMXBean::getName).forEach(System.out::println);
    }
}

// JDK 1.7与JDK 1.8一致

// JDK 1.8
/*
PS Scavenge
PS MarkSweep
 */

/*
-XX:+UseParallelGC
PS Scavenge
PS MarkSweep
 */

/*
-XX:+UseParallelOldGC
PS Scavenge
PS MarkSweep
 */

/*
-XX:+UseSerialGC
Copy
MarkSweepCompact
 */

/*
-XX:+UseParNewGC
ParNew
MarkSweepCompact
Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release
 */

/*
-XX:+UseConcMarkSweepGC
ParNew
ConcurrentMarkSweep
 */

/*
-XX:+UseG1GC
G1 Young Generation
G1 Old Generation
 */