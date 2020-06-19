package jvm.monitor;

import java.lang.management.GarbageCollectorMXBean;

/**
 * 默认Garbage Collector Metric
 *
 * @author LightMingMing
 */
public class DefaultGarbageCollectorMetric implements GarbageCollectorMetric {

    private final GarbageCollectorMXBean garbageCollectorMXBean;

    public DefaultGarbageCollectorMetric(GarbageCollectorMXBean garbageCollectorMXBean) {
        this.garbageCollectorMXBean = garbageCollectorMXBean;
    }

    @Override
    public GarbageCollectorMetricSnapshot getGcInfoSnapshot() {
        return new GarbageCollectorMetricSnapshot(garbageCollectorMXBean.getName(),
                garbageCollectorMXBean.getCollectionCount(),
                garbageCollectorMXBean.getCollectionTime());
    }
}
