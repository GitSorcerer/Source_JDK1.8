package cn.gaoh.thread.lock;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/7 8:59
 * @Version: 1.0
 */
public class CyclicBarrierTest {
    @Test
    public void test01() {
        /**
         * 集齐5名队员，开始游戏
         */
        // 开始战斗的线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("欢迎来到王者荣耀，敌军还有五秒到达战场！全军出击！");
        });
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            // lambda能操作到 i 吗
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "第" + temp + "个进入游戏！");
                try {
                    cyclicBarrier.await(); // 等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
