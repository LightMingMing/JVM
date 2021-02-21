package jvm.classloading;

import sun.misc.Launcher;

import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;

public class JDK8ClassLoaderDemo {

    public static void main(String[] args) {
        System.out.println("==============Bootstrap============");
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }
        System.out.println();

        System.out.println("==============Extension=============");
        printDirs(System.getProperty("java.ext.dirs"));

        System.out.println("==============Application=============");
        printDirs(System.getProperty("java.class.path"));

        // Application / System class loader
        ClassLoader appClassLoader = JDK8ClassLoaderDemo.class.getClassLoader();
        System.out.println(appClassLoader); // sun.misc.Launcher$AppClassLoader@73d16e93
        System.out.println(ClassLoader.getSystemClassLoader()); // sun.misc.Launcher$AppClassLoader@73d16e93

        // Extension class loader
        ClassLoader extensionClassLoader = appClassLoader.getParent();
        System.out.println(extensionClassLoader); // sun.misc.Launcher$ExtClassLoader@15db9742

        // Bootstrap class loader
        ClassLoader bootstrapClassLoader = extensionClassLoader.getParent();
        System.out.println(bootstrapClassLoader); // null
        System.out.println(String.class.getClassLoader()); // null
    }

    private static void printDirs(String dirs) {
        StringTokenizer tokenizer = new StringTokenizer(dirs, File.pathSeparator);
        int tokens = tokenizer.countTokens();
        for (int i = 0; i < tokens; i++) {
            System.out.println(tokenizer.nextToken());
        }
        System.out.println();
    }
}
