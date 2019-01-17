package com.jvm.bytecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Modify Constant_Pool_info
 *
 * @author LightMingMing
 */
public class ConstantPoolModifier {

    /**
     * <p>
     * The length of the various constants
     * </p>
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
    public static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};
    /**
     * Constant pool offset
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;
    /**
     * The tag of 'CONSTANT_Utf8_info'
     */
    private static final int CONSTANT_Utf8_info_tag = 1;
    /**
     * The tag of 'CONSTANT_FieldRef_info'
     */
    private static final int CONSTANT_FieldRef_info_tag = 9;
    /**
     * The tag of 'CONSTANT_Class_info'
     */
    private static final int CONSTANT_Class_info_tag = 7;
    /**
     * The tag of 'CONSTANT_NameAndType_info'
     */
    private static final int CONSTANT_NameAndType_info_tag = 12;
    /**
     * 1 bytes
     */
    private static final int u1 = 1;
    /**
     * 2 bytes
     */
    private static final int u2 = 2;

    public static int getConstantPoolCount(byte[] clazzBytes) {
        return ByteUtils.bytes2Int(clazzBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }

    public static byte[] modifyConstantUtf8Info(byte[] clazzBytes, String oldStr, String newStr) {
        int count = getConstantPoolCount(clazzBytes);
        int offset = CONSTANT_POOL_COUNT_INDEX + u2; // 10
        for (int i = 0; i < count; i++) {
            int tag = ByteUtils.bytes2Int(clazzBytes, offset, u1);
            if (tag == CONSTANT_Utf8_info_tag) {
                offset += u1;
                int oldLen = ByteUtils.bytes2Int(clazzBytes, offset, u2);
                String str = new String(clazzBytes, offset + u2, oldLen);
                if (oldStr.equals(str)) {
                    int newLen = newStr.length();
                    clazzBytes = ByteUtils.replace(clazzBytes, offset, u2, ByteUtils.int2Bytes(newLen, u2));
                    clazzBytes = ByteUtils.replace(clazzBytes, offset + u2, oldLen, newStr.getBytes());
                    return clazzBytes;
                } else {
                    offset += u2;
                    offset += oldLen;
                }
            } else {
                offset += ConstantPoolModifier.CONSTANT_ITEM_LENGTH[tag];
            }

        }
        return clazzBytes;
    }

    public static List<CONSTANT_info> getConstantInfos(byte[] clazzBytes) {
        int count = getConstantPoolCount(clazzBytes);
        List<CONSTANT_info> constantInfos = new ArrayList<>(count);
        int offset = CONSTANT_POOL_COUNT_INDEX + u2; // 10
        for (int i = 0; i < count; i++) {
            int tag = ByteUtils.bytes2Int(clazzBytes, offset, u1);
            if (tag == CONSTANT_Utf8_info_tag) {
                CONSTANT_Utf8_info info = new CONSTANT_Utf8_info();
                info.offset = offset;
                info.tag = tag;
                info.length = ByteUtils.bytes2Int(clazzBytes, offset += u1, u2);
                info.bytes = new String(clazzBytes, offset += u2, info.length);

                constantInfos.add(info);
                offset += info.length;

            } else if (tag == CONSTANT_FieldRef_info_tag) {
                CONSTANT_FieldRef_info info = new CONSTANT_FieldRef_info();
                info.offset = offset;
                info.tag = tag;
                info.class_index = ByteUtils.bytes2Int(clazzBytes, offset += u1, u2);
                info.name_and_type_index = ByteUtils.bytes2Int(clazzBytes, offset += u2, u2);

                constantInfos.add(info);
                offset += u2;

            } else if (tag == CONSTANT_NameAndType_info_tag) {
                CONSTANT_NameAndType_info info = new CONSTANT_NameAndType_info();
                info.offset = offset - CONSTANT_ITEM_LENGTH[tag];
                info.tag = tag;
                info.name_index = ByteUtils.bytes2Int(clazzBytes, offset += u1, u2);
                info.descriptor_index = ByteUtils.bytes2Int(clazzBytes, offset += u2, u2);

                constantInfos.add(info);
                offset += u2;

            } else if (tag == CONSTANT_Class_info_tag) {
                CONSTANT_Class_info info = new CONSTANT_Class_info();
                info.offset = offset;
                info.tag = tag;
                info.name_index = ByteUtils.bytes2Int(clazzBytes, offset += u1, u2);

                constantInfos.add(info);
                offset += u2;

            } else {
                CONSTANT_info info = new CONSTANT_info();
                info.offset = offset;
                info.tag = tag;
                constantInfos.add(info);

                offset += CONSTANT_ITEM_LENGTH[tag];
            }

        }
        return constantInfos;

    }

    private static int getFieldRefInfoIndex(List<CONSTANT_info> constantInfos, String clazzName, String nameAndType) {
        Iterator<CONSTANT_info> fieldRefInfoIterator = constantInfos.stream()
                .filter(info -> info != null && info.tag == CONSTANT_FieldRef_info_tag).iterator();
        int index = -1;
        while (fieldRefInfoIterator.hasNext()) {
            CONSTANT_FieldRef_info info = (CONSTANT_FieldRef_info) fieldRefInfoIterator.next();
            CONSTANT_Class_info classInfo = (CONSTANT_Class_info) constantInfos.get(info.class_index - 1);
            CONSTANT_Utf8_info classUtf8Info = (CONSTANT_Utf8_info) constantInfos.get(classInfo.name_index - 1);
            if (clazzName.equals(classUtf8Info.bytes)) {
                CONSTANT_NameAndType_info nameAndTypeInfo = (CONSTANT_NameAndType_info) constantInfos
                        .get(info.name_and_type_index - 1);
                CONSTANT_Utf8_info nameUtf8Info = (CONSTANT_Utf8_info) constantInfos
                        .get(nameAndTypeInfo.name_index - 1);
                CONSTANT_Utf8_info descriptorUtf8Info = (CONSTANT_Utf8_info) constantInfos
                        .get(nameAndTypeInfo.descriptor_index - 1);
                if (nameAndType.equals(nameUtf8Info.bytes + ":" + descriptorUtf8Info.bytes)) {
                    index = info.offset;
                    break;
                }
            }
        }
        return index;
    }

    public static byte[] modifyConstantFieldRefInfo(byte[] clazzBytes, String clazzName, String nameAndType,
                                                    String newClazzName) {
        int count = getConstantPoolCount(clazzBytes);
        List<CONSTANT_info> constantInfos = getConstantInfos(clazzBytes);
        int index = getFieldRefInfoIndex(constantInfos, clazzName, nameAndType);
        if (index < 0) {
            return clazzBytes;
        }
        // Update the constant pool size
        byte[] size = ByteUtils.int2Bytes(count + 2, u2);
        clazzBytes[CONSTANT_POOL_COUNT_INDEX] = size[0];
        clazzBytes[CONSTANT_POOL_COUNT_INDEX + 1] = size[1];

        // Update the class_index to the CONSTANT_FieldRef_info
        size = ByteUtils.int2Bytes(count, u2);
        clazzBytes[index + u1] = size[0];
        clazzBytes[index + u2] = size[1];

        int insertPos = constantInfos.get(count - 1).offset;

        // Add CONSTANT_Class_info of the new class
        byte[] classNameInfo = ByteUtils.int2Bytes(count + 1, u2);
        byte[] classInfo = {CONSTANT_Class_info_tag, classNameInfo[0], classNameInfo[1]};
        clazzBytes = ByteUtils.insertBytes(clazzBytes, insertPos, classInfo);

        // Add CONSTANT_Utf8_info of the new class name
        byte[] len2Bytes = ByteUtils.int2Bytes(newClazzName.length(), u2);
        byte[] utf8Info = {CONSTANT_Utf8_info_tag, len2Bytes[0], len2Bytes[1]};
        utf8Info = ByteUtils.insertBytes(utf8Info, utf8Info.length, newClazzName.getBytes());
        clazzBytes = ByteUtils.insertBytes(clazzBytes, insertPos + classInfo.length, utf8Info);

        return clazzBytes;
    }

    public static class CONSTANT_info {
        int offset;
        int tag; // u1
    }

    public static class CONSTANT_FieldRef_info extends CONSTANT_info {
        int class_index; // u2
        int name_and_type_index; // u2
    }

    public static class CONSTANT_Class_info extends CONSTANT_info {
        int name_index; // u2
    }

    public static class CONSTANT_NameAndType_info extends CONSTANT_info {
        int name_index; // u2
        int descriptor_index; // u2
    }

    public static class CONSTANT_Utf8_info extends CONSTANT_info {
        int length; // u2
        String bytes; // length;
    }

}
