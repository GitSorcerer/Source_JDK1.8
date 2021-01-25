package cn.gaoh.thread.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/7 10:59
 * @Version: 1.0
 */
//@Slf4j
public class SemaphoreTest {

    @Test
    public void test01() {
        //五个停车位
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
//                    log.debug("t"+(finalI + 1) + " ，尝试进入停车场！");
                    semaphore.acquire();
//                    log.debug("t"+(finalI + 1) + " ，停车！");
                    TimeUnit.SECONDS.sleep(1);
//                    log.debug("t"+(finalI + 1) + " ，准备离开停车场！");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t"+(i + 1) + "").start();
        }
        try {
            TimeUnit.SECONDS.sleep(20);
//            log.debug("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
