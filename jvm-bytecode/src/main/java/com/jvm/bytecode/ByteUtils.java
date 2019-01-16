package com.jvm.bytecode;

/**
 * Byte utils
 *
 * @author LightMingMing
 */
public class ByteUtils {

    public static int byte2Int(byte[] bytes, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int num = ((int) bytes[i]) & 0xff;
            sum += num << ((--len) * 8);
        }
        return sum;
    }
}
