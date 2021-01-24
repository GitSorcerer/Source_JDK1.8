package cn.gaoh.thread.pool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 任务队列
 * @Author: gaoh
 * @Date: 2021/1/24 20:15
 * @Version: 1.0
 */
public class BlockingQueue<T> {
    /**
     * 任务队列
     */
    private final Deque<T> queue = new ArrayDeque<>();

    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 队列为空 等待条件
     */
    Condition takeCondition = lock.newCondition();
    /**
     * 队列满了 等待条件
     */
    Condition putCondition = lock.newCondition();

    /**
     * 容量
     */
    private final int capacity;

    /**
     * 默认容量为16
     */
    public BlockingQueue() {
        this.capacity = 1 << 4;
    }

    /**
     * 初始化队列容量
     *
     * @param capacity 容量
     */
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 从队列里面取任务
     *
     * @return 返回任务
     */
    public T take() {
        try {
            lock.lock();
            //如果队列为空就到takeCondition中等待
            while (queue.isEmpty()) {
                try {
                    //等待
                    takeCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //取出并移除队列的第一个任务
            T t = queue.removeFirst();
            //取值后，叫醒存任务线程
            putCondition.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 有超时间的获取等待
     *
     * @param timeout
     * @param unit
     * @return
     */
    public T take(long timeout, TimeUnit unit) {
        try {
            lock.lock();
            //转换成纳秒
            long nanos = unit.toNanos(timeout);
            //如果队列为空就到takeCondition中等待
            while (queue.isEmpty()) {
                try {
                    //返回剩余时间 假如timeout为3s,当前线程在takeCondition等4s nanos=4-3=-1 超时等待 返回null
                    if (nanos <= 0) {
                        return null;
                    }
                    //等待时间
                    nanos = takeCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //取出并移除队列的第一个任务
            T t = queue.removeFirst();
            //取值后，叫醒putCondition线程
            putCondition.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 将任务放入队列
     *
     * @param task 任务
     * @return 是否存入成功
     */
    public boolean put(T task) {
        try {
            lock.lock();
            //队列满了
            while (queue.size() == capacity) {
                try {
                    //等待
                    putCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //放入队列
            queue.addLast(task);
            //唤醒 takeCondition中的线程
            takeCondition.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 将任务放入队列(带有超时时间的)
     *
     * @param task    任务
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 是否存入成功
     */
    public boolean put(T task, long timeout, TimeUnit unit) {
        try {
            lock.lock();
            //转换为纳秒
            long nanos = unit.toNanos(timeout);
            //队列满了
            while (queue.size() == capacity) {
                try {
                    //返回剩余时间
                    if (nanos <= 0) {
                        return false;
                    }
                    //等待 并获取等待时间
                    nanos = putCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //放入队列
            queue.addLast(task);
            //唤醒 takeCondition中的线程
            takeCondition.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 对策略进行处理
     *
     * @param rejectPolicy 策略
     * @param task         任务
     */
    public void process(RejectPolicy<T> rejectPolicy, T task) {
        try {
            lock.lock();
            //队列满了
            if (queue.size() == capacity) {
                //执行对应策略
                rejectPolicy.reject(this, task);
            } else {
                //否则放入队列中
                queue.addLast(task);
                //唤醒获取队列的线程
                takeCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
