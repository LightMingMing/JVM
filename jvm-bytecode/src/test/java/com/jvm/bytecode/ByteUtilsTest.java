package com.jvm.bytecode;

import org.junit.Assert;
import org.junit.Test;

public class ByteUtilsTest {

    @Test
    public void replace() {
        byte[] target = {1, 2, 3, 4, 5, 6};
        byte[] replacement = {12, 13, 14};
        Assert.assertArrayEquals(new byte[]{1, 12, 13, 14, 4, 5, 6}, ByteUtils.replace(target, 1, 2, replacement));
    }

    @Test
    public void insert() {
        byte[] target = {1, 2, 3, 4};
        Assert.assertArrayEquals(new byte[]{1, 2, 11, 3, 4}, ByteUtils.insertBytes(target, 2, new byte[]{11}));
        Assert.assertArrayEquals(new byte[]{1, 2, 3, 4, 11, 12},
                ByteUtils.insertBytes(target, 4, new byte[]{11, 12}));
    }

    @Test
    public void int2Bytes() {
        int n1 = 12;
        int n2 = 300;
        Assert.assertArrayEquals(new byte[]{0, 12}, ByteUtils.int2Bytes(n1, 2));
        Assert.assertArrayEquals(new byte[]{0, 1, 44}, ByteUtils.int2Bytes(n2, 3));
    }

}
