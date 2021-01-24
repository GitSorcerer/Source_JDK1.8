package cn.gaoh.thread.likou;

import java.util.concurrent.Semaphore;

/**
 * @Description: 水分子输出问题
 * @Author: gaoh
 * @Date: 2021/1/22 14:42
 * @Version: 1.0
 */
public class H2O {
    Semaphore h = new Semaphore(2);
    Semaphore o = new Semaphore(1);

    public H2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        h.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        if(h.availablePermits()==0){
            o.release();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        o.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        h.release(2);
    }
}
