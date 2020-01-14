package jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Heap OutOfMemory
 * VM Args: -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author LightMingMing
 */
public class HeapOOM {

    static class OOMObject {
    }

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "InfiniteLoopStatement"})
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true)
            list.add(new OOMObject());
    }
}

/*
Output:
java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid14241.hprof ...
Heap dump file created [12984134 bytes in 0.041 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:265)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:231)
	at java.util.ArrayList.add(ArrayList.java:462)
	at com.jvm.oom.HeapOOM.main(HeapOOM.java:18)
 */
