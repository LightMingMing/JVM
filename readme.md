# 深入理解JVM虚拟机
## 1. Java 内存区域
1. 程序计数器
2. Java虚拟机栈
3. 本地方法栈
4. Java堆
5. 方法区 - 类信息、常量、静态变量、即时编译器编译后的代码
6. 运行时常量池 - 方法区的一部分
    
    编译期生成的各种字面量和符号引用存放在常量池`Constant Pool Table`中,在类加载后进入方法区的运行时常量池
    
    动态性:运行期间也可以讲新的常量放入池，如`String.intern()`方法

7. 直接内存 `Direct Memory`
    
    JDK1.4 NIO, Channel/Buffer, 使用Native函数库直接分配堆外内存，然后通过一个存储在Java堆中的`DirectByteBuffer`对象作为这块内存的引用进行操作，避免了在Java堆和Native堆中来回复制数据

## 2. OutOfMemoryError异常
### 2.1 Java 堆溢出
```
-Xms 堆最小值、初始堆大小
-Xmx 堆最大值
-XX:+DumpOnOutOfMemoryError 在出现内存溢出异常时，备份出当前堆转储快照

java.lang.OutOfMemoryError:Java heap space
```   
分析堆转储快照，发现是内存泄漏`Memory Leak`还是内存溢出`MemoryOverflow`

> 内存泄漏：查看对象到GC Roots的引用链，找出垃圾收集器无法自动回收的原因
> 内存溢出：-Xms -Xmx参数是否可以继续调大; 对象生命周期是否过长,尝试减少程序运行期内存消耗

### 2.2 虚拟机栈和本地方法栈溢出
```
-Xss 栈容量
```
`StackOverflowError`请求栈深度大于虚拟机所允许的深度.
使用-Xss参数减少栈内存容量，输出StackOverflowError异常,栈的深度也相应减少(单线程)

`OutOfMemoryError`虚拟机在扩展栈时无法申请到足够的空间内存(创建多个线程)

减少最大堆和减少栈容量来换取更多的线程

### 2.3 方法区和运行时常量池溢出
```
JDK 1.7 永久带大小
-XX:PermSize
-XX:MaxPermSize

JDK 1.8 元数据区大小
-XX:MetaspaceSize
-XX:MaxMetaspaceSize
```

> String.intern() 在JDK 1.6中，把首次遇到的字符串实例复制到永久代中，返回的是永久带中这个字符串的引用；JDK 1.7 不会复制实例，只是在常量池中记录首次出现的引用
