package cn.gaoh.test.test.model;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/7/6 20:16
 * @Version: 1.0
 */
public class Something {
    // constructor methods
    public Something() {
        System.out.println("无参构造");
    }

    public Something(String something) {
        System.out.println(something);
    }

    // static methods
    public static String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }

    // object methods
    public String endWith(String s) {
        return String.valueOf(s.charAt(s.length() - 1));
    }

    void endWith() {
    }
}
