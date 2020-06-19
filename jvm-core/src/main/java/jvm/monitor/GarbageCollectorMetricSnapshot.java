package jvm.monitor;

/**
 * GC信息快照
 *
 * @author LightMingMing
 */
public class GarbageCollectorMetricSnapshot {

    private final String gcType;
    private final long gcCount;
    private final long gcTime;

    public GarbageCollectorMetricSnapshot(String gcType, long gcCount, long gcTime) {
        this.gcType = gcType;
        this.gcCount = gcCount;
        this.gcTime = gcTime;
    }

    public String getGcType() {
        return gcType;
    }

    public long getGcCount() {
        return gcCount;
    }

    public long getGcTime() {
        return gcTime;
    }

    @Override
    public String toString() {
        return String.format("GC [%12s, count %2d, time %2d]", gcType, gcCount, gcTime);
    }
}
