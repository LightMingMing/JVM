package jvm.monitor;

/**
 * 垃圾收集器组合
 *
 * @author LightMingMing
 */
public enum GarbageCollectorCombination {

    SERIAL("Copy", "MarkSweepCompact"),
    MSC("PS scavenge", "MarkSweepCompact"),
    PARALLEL("PS scavenge", "PS MarkSweep"),
    CMS("ParNew", "ConcurrentMarkSweep"),
    G1("G1 Young Generation", "G1 Old Generation");

    private final String youngGc;
    private final String oldGc;

    GarbageCollectorCombination(String youngGc, String oldGc) {
        this.youngGc = youngGc;
        this.oldGc = oldGc;
    }

    public String getYoungGc() {
        return youngGc;
    }

    public String getOldGc() {
        return oldGc;
    }
}
