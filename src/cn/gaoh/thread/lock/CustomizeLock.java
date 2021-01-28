package cn.gaoh.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description: 自定义锁
 * @Author: gaoh
 * @Date: 2021/1/27 18:25
 * @Version: 1.0
 */
public class CustomizeLock implements Lock {
    private LockSync sync;

    public CustomizeLock() {
        sync = new LockSync();
    }

    /**
     * 独占锁
     */
    static class LockSync extends AbstractQueuedSynchronizer {
        /**
         * 能获取锁返回true，获取不到返回false
         */
        @Override
        protected boolean tryAcquire(int arg) {
            //尝试将状态从0，改为1
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 解锁，state改为0，ownerThread制空
         */
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 条件队列
         */
        public Condition newCondition() {
            return new ConditionObject();
        }
    }


    /**
     * 获取锁 获取不到就入队
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 可打断锁
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试获取锁，获取不到就返回false
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 带超时时间的获取锁
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        sync.release(1);
    }

    /**
     * 条件
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
