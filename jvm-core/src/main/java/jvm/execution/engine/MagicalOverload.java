package jvm.execution.engine;

import java.io.Serializable;

/**
 * 重载
 *
 * @author LightMingMing
 */
@SuppressWarnings("unused")
public class MagicalOverload {

    // char -> int -> long -> float -> double -> Character -> Serializable -> Object -> char... -> int...  -> ... -> Object...

    public static void main(String[] args) {

        System.out.println(new MagicalOverload().hello('c'));

    }

    public String hello(char c) {
        return "Hello, char";
    }

    public String hello(int c) {
        return "Hello, int";
    }

    public String hello(long c) {
        return "Hello, long";
    }

    public String hello(double c) {
        return "Hello, double";
    }

    public String hello(Serializable c) {
        return "Hello, Serializable";
    }

    public String hello(Object c) {
        return "Hello, Object";
    }

    public String hello(char... c) {
        return "Hello, char...";
    }

    public String hello(int... c) {
        return "Hello, int...";
    }

    // ...

    public String hello(Object... c) {
        return "Hello, Object...";
    }
}
