package jvm.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * 对象实例内存布局(64虚拟机)
 * <p>
 * 开启压缩指针:
 * 对象头 mark word 8字节, klass 4字节, 对齐4字节, 共16字节
 * <p>
 * 关闭压缩指针:
 * 对象头 mark word 8字节, klass 8字节, 不用对齐, 共16字节
 * <p>
 * 通过虚拟机参数-XX:-UseCompressedOops可关闭压缩指针, 默认可能是开启
 */
public class ObjectLayout {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
    }
}

/*
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
 */