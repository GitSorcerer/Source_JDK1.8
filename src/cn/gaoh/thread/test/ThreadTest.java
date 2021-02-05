package cn.gaoh.thread.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/30 16:41
 * @Version: 1.0
 */
public class ThreadTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("running...");
        }, "t1");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(t1.getState());
            LockSupport.unpark(t1);
        } catch ( Exception e) {
            e.printStackTrace();
        }

    }
}
