package com.jvm.bytecode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ConstantPoolModifierTest {

    private static final String SYSTEM = "java/lang/System";
    private static final String CUSTOMIZE_SYSTEM = "com/jvm/bytecode/CustomizeSystem";
    private static final String RESOURCES = "src/test/resources";
    private static final int EXPECTED_POOL_SIZE_1 = 29;
    private static final int EXPECTED_POOL_SIZE_2 = 48;
    private static Path path1;
    private static Path path2;
    private static Path path3;
    private static Path path4;
    private static Path path5;
    private static Path path6;

    private byte[] clazzBytes1;
    private byte[] clazzBytes2;

    @BeforeClass
    public static void beforeClass() {
        String userDir = System.getProperty("user.dir");
        path1 = Paths.get(userDir, RESOURCES, "SimpleHelloWorld8.class");
        path2 = Paths.get(userDir, RESOURCES, "ComplexHelloWorld8.class");
        path3 = Paths.get(userDir, RESOURCES, "Simple_ModifyConstantUtf8Info.class");
        path4 = Paths.get(userDir, RESOURCES, "Complex_ModifyConstantUtf8Info.class");
        path5 = Paths.get(userDir, RESOURCES, "Simple_ModifyConstantFieldRefInfo.class");
        path6 = Paths.get(userDir, RESOURCES, "Complex_ModifyConstantFieldRefInfo.class");
    }

    @Before
    public void before() throws IOException {
        clazzBytes1 = Files.readAllBytes(path1);
        clazzBytes2 = Files.readAllBytes(path2);
    }

    @Test
    public void constantPoolCountTest() {
        Assert.assertEquals(EXPECTED_POOL_SIZE_1, ConstantPoolModifier.getConstantPoolCount(clazzBytes1));
        Assert.assertEquals(EXPECTED_POOL_SIZE_2, ConstantPoolModifier.getConstantPoolCount(clazzBytes2));
    }

    @Test
    public void modifyConstantUtf8InfoTest() throws IOException {
        clazzBytes1 = ConstantPoolModifier.modifyConstantUtf8Info(clazzBytes1, SYSTEM, CUSTOMIZE_SYSTEM);
        Files.write(path3, clazzBytes1, StandardOpenOption.CREATE);
        Assert.assertEquals(EXPECTED_POOL_SIZE_1, ConstantPoolModifier.getConstantPoolCount(clazzBytes1));

        clazzBytes2 = ConstantPoolModifier.modifyConstantUtf8Info(clazzBytes2, SYSTEM, CUSTOMIZE_SYSTEM);
        Files.write(path4, clazzBytes2, StandardOpenOption.CREATE);
        Assert.assertEquals(EXPECTED_POOL_SIZE_2, ConstantPoolModifier.getConstantPoolCount(clazzBytes2));
    }

    @Test
    public void modifyConstantFieldRefInfoTest() throws IOException {
        clazzBytes1 = ConstantPoolModifier.modifyConstantFieldRefInfo(clazzBytes1, SYSTEM,
                "out:Ljava/io/PrintStream;", CUSTOMIZE_SYSTEM);
        Files.write(path5, clazzBytes1, StandardOpenOption.CREATE);
        Assert.assertEquals(EXPECTED_POOL_SIZE_1 + 2, ConstantPoolModifier.getConstantPoolCount(clazzBytes1));

        clazzBytes2 = ConstantPoolModifier.modifyConstantFieldRefInfo(clazzBytes2, SYSTEM,
                "out:Ljava/io/PrintStream;", CUSTOMIZE_SYSTEM);
        Files.write(path6, clazzBytes2, StandardOpenOption.CREATE);
        Assert.assertEquals(EXPECTED_POOL_SIZE_2 + 2, ConstantPoolModifier.getConstantPoolCount(clazzBytes2));
    }
}
/*
public class HelloWorld8
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #6.#15         // java/lang/Object."<init>":()V
   #2 = Fieldref           #16.#17        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #18            // Hello, World.
   #4 = Methodref          #19.#20        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #21            // HelloWorld8
   #6 = Class              #22            // java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               main
  #12 = Utf8               ([Ljava/lang/String;)V
  #13 = Utf8               SourceFile
  #14 = Utf8               HelloWorld8.java
  #15 = NameAndType        #7:#8          // "<init>":()V
  #16 = Class              #23            // java/lang/System
  #17 = NameAndType        #24:#25        // out:Ljava/io/PrintStream;
  #18 = Utf8               Hello, World.
  #19 = Class              #26            // java/io/PrintStream
  #20 = NameAndType        #27:#28        // println:(Ljava/lang/String;)V
  #21 = Utf8               HelloWorld8
  #22 = Utf8               java/lang/Object
  #23 = Utf8               java/lang/System
  #24 = Utf8               out
  #25 = Utf8               Ljava/io/PrintStream;
  #26 = Utf8               java/io/PrintStream
  #27 = Utf8               println
  #28 = Utf8               (Ljava/lang/String;)V
{
  public HelloWorld8();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 1: 0

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String Hello, World.
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 4: 0
        line 5: 8
}
 */