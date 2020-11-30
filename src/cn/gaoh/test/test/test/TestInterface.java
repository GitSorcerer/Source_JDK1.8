package cn.gaoh.test.test.test;

import cn.gaoh.test.test.Interface.MyFunctionalInterface;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;


/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/7/6 19:15
 * @Version: 1.0
 */
public class TestInterface {

    @Test
    public void test() {
        Function<String, String> function1 = item -> item + "返回值";

        System.out.println(function1.apply("123"));

        Consumer<String> function2 = iterm -> {
            System.out.println(iterm);
        };//lambda语句，使用大括号，没有return关键字，表示没有返回值

        Predicate<String> function3 = iterm -> "".equals(iterm);

        Supplier<String> function4 = () -> new String("");


        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "xiaoming", "zhaoliu");

        list.stream()
                .map(value -> value + "1") //传入的是一个Function函数式接口
                .filter(value -> value.length() > 2) //传入的是一个Predicate函数式接口
                .forEach(value -> System.out.println(value)); //传入的是一个Consumer函数式接口

//        list.stream().
    }

    @Test
    public void test1() {
        /**
         * Bi类型的接口创建
         */
       /* BiFunction<String, String, Integer> biFunction = (str1, str2) -> str1.length() + str2.length();

        BiConsumer<String, String> biConsumer = (str1, str2) -> System.out.println(str1 + str2);

        BiPredicate<String, String> biPredicate = (str1, str2) -> str1.length() > str2.length();*/


        /**
         * Bi类型的接口使用
         */
        int length = getLength("hello", "world", (str1, str2) -> str1.length() + str2.length()); //输出10
        boolean boolean1 = getBoolean("hello", "world", (str1, str2) -> str1.length() > str2.length()); //输出false

        System.out.println(length);
        System.out.println(boolean1);

        noResult("hello", "world", (str1, str2) -> System.out.println(str1 + " " + str2));
    }

    public static int getLength(String str1, String str2, BiFunction<String, String, Integer> function) {
        return function.apply(str1, str2);
    }

    public static void noResult(String str1, String str2, BiConsumer<String, String> biConcumer) {
        biConcumer.accept(str1, str2);
    }

    public static boolean getBoolean(String str1, String str2, BiPredicate<String, String> biPredicate) {
        return biPredicate.test(str1, str2);
    }

    @Test
    public void test2() {
        String str1 = getLength1("hello", value -> "hello的长度：" + value, value -> value.length()); //输出:hello的长度：5
        System.out.println(str1);

        Integer result = getLength2("hello", value -> value, value -> value.length()); //输出：5
        System.out.println(result);
    }

    public static String getLength1(String str1, Function<Integer, String> function1, Function<String, Integer> function2) {
        /**
         * 这里一定要注意，function1和function2的参数类型。
         * function2的输出类型与function1的输入类型一定要一致，
         * 否则编译不会通过
         */
        return function1.compose(function2).apply(str1);
    }

    public static Integer getLength2(String str1, Function<String, String> function1, Function<String, Integer> function2) {
        /**
         * 这里一定要注意，function1和function2的参数类型。
         * function1的输出类型与function2的输入类型一定要一致，(方向相反)
         * 否则编译不会通过
         */
        return function1.andThen(function2).apply(str1);
    }

    @Test
    public void test3() {
        MyFunctionalInterface functionalInterface = message -> System.out.println("Hello " + message);
    }


    /***
     * java.lang.Runnable,
     *
     * java.awt.event.ActionListener,
     *
     * java.util.Comparator,
     *
     * java.util.concurrent.Callable
     *
     * java.util.function包下的接口，如Consumer、Predicate、Supplier等
     */

    /***
     *
     *
     * Consumer-消费者
     * Consumer<T>          提供一个T类型的输入参数，不返回执行结果
     * BiConsumer<T,U>      提供两个自定义类型的输入参数，不返回执行结果
     * DoubleConsumer       表示接受单个double值参数，但不返回结果的操作
     * IntConsumer          表示接受单个int值参数，但不返回结果的操作
     * LongConsumer         表示接受单个long值参数，但不返回结果的操作
     * ObjDoubleConsumer<T> 表示接受object值和double值，但是不返回任何操作结果
     * ObjIntConsumer<T>    表示接受object值和int值，但是不返回任何操作结果
     * ObjLongConsumer<T>   表示接受object值和long值，但是不返回任何操作结果
     *
     *
     * void  accept(T t)
     * 对给定的参数执行操作
     * default  Consumer<T>  andThen(Consumer<? super T> after)
     * 返回一个组合函数，after将会在该函数执行之后应用
     *
     */
    @Test
    public void test4() {

        StringBuilder sb = new StringBuilder("123");
        Consumer<StringBuilder> consumer = (str) -> str.append("456");
        consumer.accept(sb);
        System.out.println(sb);

        Consumer<StringBuilder> builderConsumer = (str) -> str.append("9999");
        consumer.andThen(builderConsumer).accept(sb);
        System.out.println(sb);


        sb = new StringBuilder();
        StringBuilder finalSb = sb;
        BiConsumer<String, String> biConsumer = (a, b) -> {
            finalSb.append(a);
            finalSb.append(b);
        };
        biConsumer.accept("Hello ", "Jack!");
        System.out.println(sb.toString());


        BiConsumer<String, String> biConsumer1 = (a, b) -> {
            System.out.println(a + b);
        };
        biConsumer.andThen(biConsumer1).accept("Hello", " Jack!");
    }

    /***
     *
     * Predicate-谓语
     * Predicate<T>	对给定的输入参数执行操作，返回一个boolean类型的结果（布尔值函数）
     * BiPredicate<T,U>	对给定的两个输入参数执行操作，返回一个boolean类型的结果（布尔值函数）
     * DoublePredicate	对给定的double参数执行操作，返回一个boolean类型的结果（布尔值函数）
     * IntPredicate	对给定的int输入参数执行操作，返回一个boolean类型的结果（布尔值函数）
     * LongPredicate	对给定的long参数执行操作，返回一个boolean类型的结果（布尔值函数）
     *
     *
     * Predicate<T>
     * 对给定的输入参数执行操作，返回一个boolean类型的结果（布尔值函数）
     *
     * boolean  test(T t)
     * 根据给定的参数进行判断
     * Predicate<T>  and(Predicate<? super T> other)
     * 返回一个组合判断，将other以短路并且的方式加入到函数的判断中
     * Predicate<T>  or(Predicate<? super T> other)
     * 返回一个组合判断，将other以短路或的方式加入到函数的判断中
     * Predicate<T>  negate()
     * 将函数的判断取反
     */


    /***
     * Function-功能
     *
     * Function<T,R>
     * 接收一个参数并返回结果的函数
     * BiFunction<T,U,R>
     * 接受两个参数并返回结果的函数
     * DoubleFunction<R>
     * 接收一个double类型的参数并返回结果的函数
     * DoubleToIntFunction
     * 接收一个double类型的参数并返回int结果的函数
     * DoubleToLongFunction
     * 接收一个double类型的参数并返回long结果的函数
     * IntFunction<R>
     * 接收一个int类型的参数并返回结果的函数
     * IntToDoubleFunction
     * 接收一个int类型的参数并返回double结果的函数
     * IntToLongFunction
     * 接收一个int类型的参数并返回long结果的函数
     * LongFunction<R>
     * 接收一个long类型的参数并返回结果的函数
     *
     * LongToDoubleFunction
     * 接收一个long类型的参数并返回double结果的函数
     * LongToIntFunction
     * 接收一个long类型的参数并返回int结果的函数
     * ToDoubleBiFunction<T,U>
     * 接收两个参数并返回double结果的函数
     * ToDoubleFunction<T>
     * 接收一个参数并返回double结果的函数
     * ToIntBiFunction<T,U>
     * 接收两个参数并返回int结果的函数
     * ToIntFunction<T>
     * 接收一个参数并返回int结果的函数
     * ToLongBiFunction<T,U>
     * 接收两个参数并返回long结果的函数
     * ToLongFunction<T>
     * 接收一个参数并返回long结果的函数
     *
     *
     * R  apply(T t)	将此参数应用到函数中
     *   Function<T, R>  andThen(Function<? super R,? extends V> after)	返回一个组合函数，该函数结果应用到after函数中
     *   Function<T, R>  compose(Function<? super V,? extends T> before)	返回一个组合函数，首先将入参应用到before函数，再将before函数结果应用到该函数中
     */


    @Test
    public void test5() {
        DoubleConsumer doubleConsumer = System.out::println;
        doubleConsumer.accept(1.3); // 1.3
    }



}
