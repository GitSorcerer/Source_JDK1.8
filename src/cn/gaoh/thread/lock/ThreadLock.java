package cn.gaoh.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/12/19 9:31
 * @Version: 1.0
 */
public class ThreadLock {
    private static /*volatile*/ int num = 1;
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                add();
            }
        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                add();
            }
        }, "t2").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                add();
            }
        }, "t3").start();

    }

    public static void add() {
        try {
            lock.lock();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("ThreadName:" + Thread.currentThread().getName() + " num:" + num++);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
