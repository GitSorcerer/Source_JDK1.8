package cn.gaoh.thread.likou;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/21 22:28
 * @Version: 1.0
 */
public class Foo {
    public CountDownLatch second = new CountDownLatch(1);
    public CountDownLatch third = new CountDownLatch(1);
    public Foo() {
    }
    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        second.countDown();
    }
    public void second(Runnable printSecond) throws InterruptedException {
        second.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        third.countDown();
    }
    public void third(Runnable printThird) throws InterruptedException {
        third.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}