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

> String.intern() 在JDK 1.6中，把首次遇到的字符串实例复制到永久代中，返回的是永久代中这个字符串的引用；JDK 1.7 不会复制实例，只是在常量池中记录首次出现的引用

### 2.4 直接内存溢出
```
-XX:MaxDirectMemorySize
```
`DirectByteBuffer`分配内存也会抛出内存溢出异常，但它抛出异常时并没有真正向操作系统申请分配内存，而是通过计算无法分配，于是手动抛出异常；

真正申请内存分配的方法是`unsafe.allocateMemory()` --->  **但测试代码没有达到理想效果, /(ㄒoㄒ)/~~**

## 3. 垃圾收集与内存分配--这样知道为什么我没有对象了！
### 3.1 在或不在
1. 引用计数 Reference Counting *简单高效 -- 循环引用*
2. 可达性分析 Reachability Analysis
   
    GC Roots , Reference Chain引用链
    
    可作为GC Roots的对象：
    1. 虚拟机栈中引用的对象
    2. 方法区中类静态属性引用的对象
    3. 方法区中常量引用的对象
    4. 本地方法栈中JNI(Native方法)引用的对象
3. finalize()-- 拥有**一次**自我救赎的机会, 还是最好不要用，不可控性太大
4. 方法区回收--废弃常量、无用类
   
    废弃常量：常量池中常量，没有任何对象引用，也没有其他地方引用这个字面量
    
    无用的类
    1. 所有实例已回收
    2. 加载该类的类加载器已回收
    3. 类对应的Class对象没有在任何地方被引用


    引用
    Strong Reference 强引用-- GC 永不发生
    Soft Reference 软引用-- 有用非必须的对象，内存溢出前进行GC，没有足够内存才会抛出内存溢出异常
    Weak Reference 弱引用-- 描述非必须对象， 只能生存在下一次垃圾收集之前
    Phantom Reference 虚引用-- 虚引用对对象的生存没有任何影响，无法通过一个虚引用取得一个对象实例

### 3.2 垃圾收集算法
1. 标记-清除 Mark-Sweep 标记清除两个过程的效率都比较低，空间碎片
2. 复制算法  Copying 简单高效--代价太高， 适用于存活率较低的场景
3. 标记-整理算法 Mark-Compact,存活对象向一端移动，然后清除掉边界以外的内存
4. 分代收集

### 3.3 HotSpot相关算法
1. 枚举根节点-GC Roots, OopMap(Ordinary Object Point)数据结构，在类加载完成的时候，对象内什么偏移量上是什么类型的数据就已计算出来，在JIT编译时也会存放栈、寄存器中引用位置
2. 安全点SafePoint-特定位置记录OopMap, 方法调动、循环跳转、异常跳转；用户线程停顿

    抢先式中断 Preemptive Suspension, GC发生时，让线程全部中断，有不在安全点的，就恢复执行到安全点
    主动式中断 Voluntary Suspension, 设置一个标志，各线程执行时主动去轮询这个标志，发现中断标志为真时，就自己中断挂起
    
3. 安全区域Safe Region, 程序处于Sleep状态或Blocked状态时，程序无法响应中断请求

    安全区域，一段代码中，引用关系不会发生变化，在这个区域中的任何地方GC都是安全的，离开时，要检测是否完成了GC过程

### 3.4 垃圾收集器
1. Serial--Client模式下新生代收集器，单线程收集器，简单高效
2. ParNew--新生代并行收集，默认开启线程收集数量与CPU数量一致
3. Parallel Scavenge --JDK1.4 新生代复制算法收集器，目标是达到一个可控的吞吐量(Throughput=UserTime/UserTime+GcTime),别的收集器更多的关注停顿时间
   
   (GC Ergonomics)GC自适应调节策略-XX:+UserAdaptiveSizePolicy，不需要手动设置-Xmn(新生代) -XX:SurvivorRatio -XX:PretenureSizeThreshold(晋升老年代大小)
4. Serial Old--老年代标记整理，单线程
5. Parallel Old--JDK1.6 老年代标记整理，多线程 (为了与Parallel Scavenge更好的搭配)
6. CMS/Concurrency Mark Sweep--JDK1.5 老年代标记-清除, 具有划时代的意义(用户线程、GC线程并行执行) 
7. G1/Garbage First--使命：替换掉CMS收集器


    CMS缺点：
    1. CPU资源敏感 (用户线程、GC线程)
    2. 浮动垃圾，Concurrent Mode Failure，会临时启动Serial Old收集器，造成更长停顿时间
    3. 空间碎片
    
    Garbage First优势:
    1. 并行、并发：缩短Stop-The-World停顿时间
    2. 分代收集: 采用不同的方式处理新对象、存活一段时间的对象、熬过多次GC的旧对象
    3. 空间整合: 整体上标记-整理，局部上(两个Region)基于复制
    4. 可预测停顿

### 3.5 内存分配与回收策略
1. 对象优先在Eden分配

    Eden区没有足够的空间进行分配时，虚拟机发起一次Minor GC.
    > Full GC/Major GC:指发生在老年代的GC,速度一般比Minor GC慢10倍以上 
    
2. 大对象直接进入老年代
   
    大对象：连续内存空间的Java对象，更可怕的是一群短命大对象
    -XX:PretenureSizeThreshold, 大于这个参数的对象直接在老年代分配。该参数只对**Serial、ParNew**有效
    
3. 长期存活对象进入老年代
   
   算算你对象的年龄：
   对象出生于Eden区，经历一次Minor GC仍然存活并且能够移动到Survivor空间中，年龄设为1。以后每经历过一个Minor GC,age+1。
   默认15岁，就是个老年人了... 可以通过-XX:MaxTenuringThreshold设置
   
4. 动态对象年龄判定--如果相同年龄对象大小总和大于Survivor空间的一半，那么年龄大于或等于该年龄的对象可以进入老年代

5. 空间分配担保
```js
if (老年代最大可用连续空间 > 新生代所有对象可用空间) {
    return Minor_GC_Safe  
} else if (HandlePromotionFailure) { // 允许担保失败
    if (老年代最大可用空间连续空间 > 历次晋升到老年代对象的平均大小)
        return Minor_GC; // 尝试进行GC
    else
        return Full_GC;
} else {
    return Full_GC;
}
```
