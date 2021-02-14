package jvm.oom;

/**
 * String constant pool
 * <p>
 * JDK1.7+ intern方法, 如果string pool池中存在相等(equals方法)的字符串, 则返回池中对象引用; 否则将对象放入池中后返回对象引用.
 * <p>
 * 方法区/元数据区 运行时常量池
 *
 * @see sun.misc.Version
 * @see System#initializeSystemClass()
 * @see <a href='https://www.bilibili.com/video/BV1Hy4y1B78T?t=87&p=3'>哔哩哔哩讲解视频</a>
 */
@SuppressWarnings({"StringEquality", "JavadocReference"})
public class StringConstantPool {

    public static void main(String[] args) {
        String s = "MingMing";
        System.out.println(s == s.intern()); // true

        StringBuilder sb = new StringBuilder().append("Ming").append("01");

        s = sb.toString();
        System.out.println(s == s.intern()); // 放入池中, 返回引用. true

        sb = new StringBuilder().append("ja").append("va");
        s = sb.toString();
        System.out.println(s == s.intern()); // "java"字符串已存在于池中. false
    }

}

// javap -verbose StringConstantPool
/*
Constant pool:
   #2 = String             #39            // MingMing
   #8 = String             #47            // Ming
  #10 = String             #49            // 01
  #12 = String             #51            // ja
  #13 = String             #52            // va
 */
