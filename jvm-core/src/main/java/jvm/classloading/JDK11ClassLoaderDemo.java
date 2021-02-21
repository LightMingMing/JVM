package jvm.classloading;

public class JDK11ClassLoaderDemo {

    public static void main(String[] args) {
        // application/system class loader
        ClassLoader appClassLoader = JDK11ClassLoaderDemo.class.getClassLoader();
        System.out.println(appClassLoader); // jdk.internal.loader.ClassLoaders$AppClassLoader@4b85612c

        // platform class loader
        ClassLoader platformClassLoader = appClassLoader.getParent();
        System.out.println(platformClassLoader); // jdk.internal.loader.ClassLoaders$PlatformClassLoader@cac736f

        // bootstrap class loader
        ClassLoader bootClassLoader = platformClassLoader.getParent();
        System.out.println(bootClassLoader); // null

        System.out.println(String.class.getClassLoader()); // null
    }
}
