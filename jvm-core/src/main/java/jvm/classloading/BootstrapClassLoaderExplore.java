package jvm.classloading;

import java.util.ArrayList;

/**
 * Explore: Bootstrap Class Loader
 *
 * @author LightMingMing
 */
public class BootstrapClassLoaderExplore {

    public static void main(String[] args) {

        System.out.println(ArrayList.class.getClassLoader()); // is null

    }

}
/*
output:
null
 */
