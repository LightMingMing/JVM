package jvm.classloading;

/**
 * Explore: Application Class Loader
 *
 * @author LightMingMing
 */
public class ApplicationClassLoaderExplore {

    public static void main(String[] args) {
        System.out.println(ApplicationClassLoaderExplore.class.getClassLoader()); // Application Class Loader
        System.out.println(ClassLoader.getSystemClassLoader()); // Application Class Loader

    }
}
/*
output:
sun.misc.Launcher$AppClassLoader@18b4aac2
sun.misc.Launcher$AppClassLoader@18b4aac2
 */