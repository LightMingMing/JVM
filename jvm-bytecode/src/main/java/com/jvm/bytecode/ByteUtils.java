package com.jvm.bytecode;

/**
 * Byte utils
 *
 * @author LightMingMing
 */
public class ByteUtils {

    public static int bytes2Int(byte[] bytes, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int num = ((int) bytes[i]) & 0xff;
            sum += num << ((--len) * 8);
        }
        return sum;
    }

    public static byte[] int2Bytes(int num, int len) {
        byte[] bytes = new byte[len];

        int next;
        while ((next = num >> 8) > 0) {
            bytes[--len] = (byte) (num - (next << 8));
            num = next;
        }
        bytes[--len] = (byte) num;
        return bytes;
    }

    public static byte[] replace(byte[] target, int targetPos, int length, byte[] replacement) {
        int interval = replacement.length - length;
        int len = target.length + (interval > 0 ? interval : 0);
        byte[] result = new byte[len];
        System.arraycopy(target, 0, result, 0, targetPos);
        System.arraycopy(replacement, 0, result, targetPos, replacement.length);
        System.arraycopy(target, targetPos + length, result, targetPos + replacement.length,
                target.length - targetPos - length);
        return result;
    }

    public static byte[] insertBytes(byte[] target, int targetPos, byte[] insert) {
        byte[] result = new byte[target.length + insert.length];
        System.arraycopy(target, 0, result, 0, targetPos);
        System.arraycopy(insert, 0, result, targetPos, insert.length);
        System.arraycopy(target, targetPos, result, targetPos + insert.length, target.length - targetPos);
        return result;
    }

}
