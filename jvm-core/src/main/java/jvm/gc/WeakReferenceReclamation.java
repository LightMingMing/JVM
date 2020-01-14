package jvm.gc;

import java.lang.ref.WeakReference;

/**
 * WeakReference 弱引用对象在下一次垃圾收集时回收
 *
 * @author LightMingMing
 */
public class WeakReferenceReclamation {

    public static void main(String[] args) {
        WeakReference<Chunk> weakReference = new WeakReference<>(new Chunk());
        System.out.println(weakReference.get()); // not null
        System.gc();
        System.out.println(weakReference.get()); // null
    }

    static class Chunk {
    }
}
/*
jvm.gc.WeakReferenceReclamation$Chunk@1175e2db
null
 */