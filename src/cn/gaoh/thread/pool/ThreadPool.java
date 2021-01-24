package cn.gaoh.thread.pool;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/24 20:48
 * @Version: 1.0
 */
public class ThreadPool {
    /**
     * 队列
     */
    private BlockingQueue<Runnable> queue;

    /**
     * 线程集合：存储正在执行的线程
     */
    private  HashSet<Worker> workers;
    private  RejectPolicy<Runnable> rejectPolicy;

    /**
     * 核心线程数
     */
    private  int coreSize;

    /**
     * 超时时间
     */
    private long timeout;

    /**
     * 时间类型
     */
    private TimeUnit unit;

    public ThreadPool(int coreSize, long timeout, TimeUnit unit, int capacity, RejectPolicy<Runnable> rejectPolicy) {
        this.queue = new BlockingQueue<>(capacity);
        workers = new HashSet<>(coreSize);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.unit = unit;
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        synchronized (workers) {
            //判断正在执行的任务是否<核心数
            if (workers.size() < coreSize) {
                //创建任务对象
                Worker worker = new Worker(task);
                //工作线程
                workers.add(worker);
                worker.start();
            } else {
                //交给策略
                queue.process(rejectPolicy, task);
            }
        }

    }

    /**
     * 任务对象
     */
    class Worker extends Thread {
        private Runnable task;

        Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //从队列里获取任务
            while (task != null || (task = queue.take(timeout, unit)) != null) {
                try {
                    //执行任务
                    task.run();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                workers.remove(this);
            }
        }
    }
}
