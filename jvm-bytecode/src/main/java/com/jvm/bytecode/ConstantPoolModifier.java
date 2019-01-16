package com.jvm.bytecode;

/**
 * Modify Constant_Utf8_info
 *
 * @author LightMingMing
 */
public class ConstantPoolModifier {

    /**
     * Constant pool offset
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * The tag of 'CONSTANT_Utf8_info'
     */
    private static final int CONSTANT_Utf8_info_tag = 1;

    /**
     * <p>The length of the various constants</p>
     * <ul>
     * <li>tag = 1, length is not fixed</li>
     * <li>tag = 3, length = 5</li>
     * <li>tag = 4, length = 5</li>
     * <li>tag = 5, length = 9</li>
     * <li>tag = 6, length = 9</li>
     * <li>tag = 7, length = 3</li>
     * <li>tag = 8, length = 3</li>
     * <li>tag = 9, length = 5</li>
     * <li>tag = 10, length = 5</li>
     * <li>tag = 11, length = 5</li>
     * <li>tag = 12, length = 5</li>
     * </ul>
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    /**
     * 1 bytes
     */
    private static final int u1 = 1;
    /**
     * 2 bytes
     */
    private static final int u2 = 2;


    public static int getConstantPoolCount(byte[] clazzBytes) {
        return ByteUtils.byte2Int(clazzBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
