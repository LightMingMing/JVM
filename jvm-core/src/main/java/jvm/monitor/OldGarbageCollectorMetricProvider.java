package jvm.monitor;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 老年代GC度量Provider
 *
 * @author LightMingMing
 */
public class OldGarbageCollectorMetricProvider implements Supplier<GarbageCollectorMetric> {

    private static final String UNKNOWN = "unknownOld";

    private final List<String> oldGcTypes = Arrays.asList("PS MarkSweep", "MarkSweepCompact", "ConcurrentMarkSweep", "G1 Old Generation");

    private final Map<String, GarbageCollectorMXBean> gcMXBeanMap = createMap();

    private static Map<String, GarbageCollectorMXBean> createMap() {
        Map<String, GarbageCollectorMXBean> map = new HashMap<>();
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            map.put(garbageCollectorMXBean.getName(), garbageCollectorMXBean);
        }
        return map;
    }

    @Override
    public GarbageCollectorMetric get() {
        String gcType = oldGcTypes.stream().filter(gcMXBeanMap::containsKey).findFirst().orElse(UNKNOWN);
        if (gcType.equals(UNKNOWN)) {
            return new UnknownOldGarbageCollectorMetric();
        }
        return new DefaultGarbageCollectorMetric(gcMXBeanMap.get(gcType));
    }

    private static class UnknownOldGarbageCollectorMetric implements GarbageCollectorMetric {

        @Override
        public GarbageCollectorMetricSnapshot getGcInfoSnapshot() {
            return new GarbageCollectorMetricSnapshot(UNKNOWN, 0, 0);
        }
    }
}
