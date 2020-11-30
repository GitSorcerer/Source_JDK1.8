package cn.gaoh.test.test.test;


import cn.gaoh.test.test.Interface.IConvert;
import cn.gaoh.test.test.model.Something;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/7/6 20:20
 * @Version: 1.0
 */
public class SomeTest {
    public static void main(String[] args) {
        test();
    }
    public static void test() {
        IConvert<String, String> convert = Something::startsWith;
        String converted = convert.convert("123");

        System.out.println(converted);
        Something something = new Something();
        IConvert<String, String> converter = something::endWith;
        converted = converter.convert("Java");
        System.out.println(converted);

        // constructor methods
        IConvert<String, Something> somethingIConvert = Something::new;
        something = somethingIConvert.convert("constructors");
        System.out.println(something);
    }
}
