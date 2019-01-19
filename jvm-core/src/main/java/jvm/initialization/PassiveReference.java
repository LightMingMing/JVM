package jvm.initialization;

/**
 * Passive reference not initialization
 *
 * @author LightMingMing
 */
public class PassiveReference {

    public static void main(String[] args) {
        int val = Sub.value; // 静态编译 -- Super.value, 只有Super会被初始化
        String hello = Sub.HELLO;
    }

    public static class Super {

        public static int value = 123;

        static {
            System.out.println("Super class initializing");
        }
    }

    public static class Sub extends Super {

        public static final String HELLO = "HELLO"; // 编译阶段已进入常量池

        static {
            System.out.println("Sub class initializing");
        }
    }
}

/*
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=3, args_size=1
         0: getstatic     #2                  // Field jvm/initialization/PassiveReference$Sub.value:I
         3: istore_1
         4: ldc           #4                  // String HELLO
         6: astore_2
         7: return
      LineNumberTable:
        line 11: 0
        line 12: 4
        line 14: 7
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       8     0  args   [Ljava/lang/String;
            4       4     1   val   I
            7       1     2 hello   Ljava/lang/String;
 */