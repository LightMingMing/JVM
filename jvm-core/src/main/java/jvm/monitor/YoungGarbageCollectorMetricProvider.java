package jvm.monitor;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 新生代GC度量Provider
 *
 * @author LightMingMing
 */
public class YoungGarbageCollectorMetricProvider implements Supplier<GarbageCollectorMetric> {

    private static final String UNKNOWN = "unknownYoung";

    private final List<String> youngGcTypes = Arrays.asList("PS Scavenge", "Par New", "G1 Young Generation");

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
        String gcType = youngGcTypes.stream().filter(gcMXBeanMap::containsKey).findFirst().orElse(UNKNOWN);
        if (gcType.equals(UNKNOWN)) {
            return new UnknownYoungGarbageCollectorMetric();
        }
        return new DefaultGarbageCollectorMetric(gcMXBeanMap.get(gcType));
    }

    private static class UnknownYoungGarbageCollectorMetric implements GarbageCollectorMetric {

        @Override
        public GarbageCollectorMetricSnapshot getGcInfoSnapshot() {
            return new GarbageCollectorMetricSnapshot(UNKNOWN, 0, 0);
        }
    }
}
