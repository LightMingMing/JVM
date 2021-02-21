package jvm.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * 数组实例内存布局(64虚拟机)
 * <p>
 * 开启压缩指针:
 * 对象头 mark word 8字节, klass 4字节, 数组长度4字节, 不用对齐, 共16字节
 * <p>
 * 关闭压缩指针:
 * 对象头 mark word 8字节, klass 8字节, 数组长度4字节, 对齐4字节, 共24字节
 * <p>
 * 通过虚拟机参数-XX:-UseCompressedOops可关闭压缩指针, 默认可能是开启
 */
public class ArrayLayout {

    public static void main(String[] args) {
        byte[] arr = new byte[Integer.MAX_VALUE - 2]; // 开启压缩指针
        // byte[] arr = new byte[Integer.MAX_VALUE - 3]; // 关闭压缩指针

        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
    }
}

/*
开启压缩指针
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           f5 00 00 f8 (11110101 00000000 00000000 11111000) (-134217483)
     12     4        (object header)                           fd ff ff 7f (11111101 11111111 11111111 01111111) (2147483645)
     16 2147483645   byte [B.<elements>                             N/A
 2147483661     3        (loss due to the next object alignment)
 */

/*
关闭压缩指针
[B object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           a8 f7 a5 06 (10101000 11110111 10100101 00000110) (111540136)
     12     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     16     4        (object header)                           fc ff ff 7f (11111100 11111111 11111111 01111111) (2147483644)
     20     4        (alignment/padding gap)
     24 2147483644   byte [B.<elements>                             N/A
 2147483668     4        (loss due to the next object alignment)
 */
