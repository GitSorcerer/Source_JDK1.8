package cn.gaoh.test.test.Interface;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/7/6 19:12
 * @Version: 1.0
 */
@FunctionalInterface
public interface MyFunctionalInterface {
    void run(String message);


    // java.lang.Object中的方法不是抽象方法
    boolean equals(Object var1);

    // default不是抽象方法
    default void defaultMethod() {

    }

    // static不是抽象方法
    static void staticMethod() {

    }
}
