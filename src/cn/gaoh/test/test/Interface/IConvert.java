package cn.gaoh.test.test.Interface;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/7/6 20:19
 * @Version: 1.0
 */
@FunctionalInterface
public interface IConvert<F, T> {
    T convert(F form);
}
