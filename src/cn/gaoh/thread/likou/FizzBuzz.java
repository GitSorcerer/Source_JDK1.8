package cn.gaoh.thread.likou;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/22 14:59
 * @Version: 1.0
 */
public class FizzBuzz {
    private int n;

    Semaphore numberSemaphore = new Semaphore(1);
    Semaphore fizzSemaphore = new Semaphore(0);
    Semaphore buzzSemaphore = new Semaphore(0);
    Semaphore fizzbuzzSemaphore = new Semaphore(0);

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz". 3
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            if (i % 5 != 0) {
                fizzSemaphore.acquire();
                printFizz.run();
                judgmentNextNum(i + 1);
            }
        }
    }

    // printBuzz.run() outputs "buzz". 5
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            if (i % 3 != 0) {
                buzzSemaphore.acquire();
                printBuzz.run();
                judgmentNextNum(i + 1);
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz". 3 5
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            fizzbuzzSemaphore.acquire();
            printFizzBuzz.run();
            judgmentNextNum(i + 1);
        }
    }

    public void judgmentNextNum(int num) {//15
        if (num % 5 == 0 && num % 3 != 0) {//5
            buzzSemaphore.release();
        } else if (num % 5 != 0 && num % 3 == 0) {//3
            fizzSemaphore.release();
        } else if (num % 5 == 0 && num % 3 == 0) {//3 5
            fizzbuzzSemaphore.release();
        } else {
            numberSemaphore.release();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer. n
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 5 != 0 && i % 3 != 0) {
                numberSemaphore.acquire();
                printNumber.accept(i);
                judgmentNextNum(i + 1);
            }
        }
    }
}
