package cn.gaoh.thread.likou;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @Description: 0 奇数 偶数 输出问题
 * @Author: gaoh
 * @Date: 2021/1/21 23:48
 * @Version: 1.0
 */
public class ZeroEvenOdd {

    private int n;
    private Semaphore zeroSemaphore, evenSemaphore, oddSemaphore;


    public ZeroEvenOdd(int n) {
        this.n = n;
        zeroSemaphore = new Semaphore(1);
        evenSemaphore = new Semaphore(0);
        oddSemaphore = new Semaphore(0);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSemaphore.acquire();
            printNumber.accept(0);
            if ((i & 1) == 0)
                evenSemaphore.release();
            else
                oddSemaphore.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSemaphore.acquire();
            printNumber.accept(i);
            zeroSemaphore.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSemaphore.acquire();
            printNumber.accept(i);
            zeroSemaphore.release();
        }
    }
}
