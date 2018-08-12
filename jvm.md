#深入理解JVM虚拟机
##1. OutOfMemoryError异常
###1.1 Java 堆溢出
```
-Xms 堆最小值
-Xmx 堆最大值
-XX:+DumpOnOutOfMemoryError 在出现内存溢出异常时，备份出当前堆转储快照
```   
分析堆转储快照，发现是内存泄漏`Memory Leak`还是内存溢出`MemoryOverflow`
###1.2 虚拟机栈和本地方法栈溢出
```
-Xss 栈容量
```
`StackOverflowError`请求栈深度大于虚拟机所允许的深度.
使用-Xss参数减少栈内存容量，输出StackOverflowError异常,栈的深度也相应减少(单线程)

`OutOfMemoryError`虚拟机在扩展栈时无法申请到足够的空间内存(创建多个线程)

减少最大堆和减少栈容量来换取更多的线程