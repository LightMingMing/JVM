package jvm.monitor;

/**
 * Garbage collector metric
 *
 * @author LightMingMing
 */
public interface GarbageCollectorMetric {
    GarbageCollectorMetricSnapshot getGcInfoSnapshot();
}
