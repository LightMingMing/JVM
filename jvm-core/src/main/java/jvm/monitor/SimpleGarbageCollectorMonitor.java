package jvm.monitor;

/**
 * 控制台
 *
 * @author LightMingMing
 */
public class SimpleGarbageCollectorMonitor {

    private final GarbageCollectorMetric youngGcMetric;

    private final GarbageCollectorMetric oldGcMetric;

    public SimpleGarbageCollectorMonitor() {
        this.youngGcMetric = new YoungGarbageCollectorMetricProvider().get();
        this.oldGcMetric = new OldGarbageCollectorMetricProvider().get();
    }

    public void printYoungGcInfo() {
        System.out.println(youngGcMetric.getGcInfoSnapshot());
    }

    public void printOldGcInfo() {
        System.out.println(oldGcMetric.getGcInfoSnapshot());
    }

    public void printGcInfo() {
        System.out.println(youngGcMetric.getGcInfoSnapshot() + ", " + oldGcMetric.getGcInfoSnapshot());
    }

}
