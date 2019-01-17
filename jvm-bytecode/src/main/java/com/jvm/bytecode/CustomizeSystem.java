package com.jvm.bytecode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Customize System
 *
 * @author LightMingMing
 */
public final class CustomizeSystem {

    public static PrintStream out = null;

    static {
        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/src/test/resources/CustomizeSystem.txt");
            out = new PrintStream(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private CustomizeSystem() {
    }
}
