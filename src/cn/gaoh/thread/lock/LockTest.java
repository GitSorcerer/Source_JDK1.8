package cn.gaoh.thread.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/27 18:39
 * @Version: 1.0
 */
public class LockTest {
    public static void main(String[] args) {
        CustomizeLock lock = new CustomizeLock();

        new Thread(() -> {
            lock.lock();

            System.out.println(Thread.currentThread().getName() + ":running...");

            lock.unlock();
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":running...");
            lock.unlock();
        }, "t2").start();
    }
}
