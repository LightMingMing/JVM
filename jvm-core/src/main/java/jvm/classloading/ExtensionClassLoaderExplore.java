package jvm.classloading;

import java.io.File;
import java.util.StringTokenizer;

/**
 * Explore: Extension Class Loader
 *
 * <p>Prepare: copy the <code>jvm-core-1.0.jar</code> to <code>~/Library/Java/Extensions</code></p>
 *
 * @author LightMingMing
 */
public class ExtensionClassLoaderExplore {

    public static void main(String[] args) {
        String extDirs = System.getProperty("java.ext.dirs");
        if (extDirs != null) {
            StringTokenizer tokenizer = new StringTokenizer(extDirs, File.pathSeparator);
            for (int i = 0; i < tokenizer.countTokens(); i++) {
                System.out.println(tokenizer.nextToken());
            }
        }

        System.out.println(ExtensionClassLoaderExplore.class.getClassLoader());
    }
}

/*
[JDK8]
/Users/mingming/Library/Java/Extensions
/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext
/Library/Java/Extensions
sun.misc.Launcher$ExtClassLoader@6ff3c5b5
 */
