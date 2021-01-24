package cn.gaoh.thread.pool;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: gaoh
 * @Date: 2021/1/24 21:21
 * @Version: 1.0
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(Runtime.getRuntime().availableProcessors(), 1000,
                TimeUnit.MILLISECONDS, 10,
                ((queue, task) -> {
                    //queue.put(task);
                    queue.put(task, 1500, TimeUnit.SECONDS);
                }));
        for (int i = 0; i < 5; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + j);
            });
        }
    }
}
