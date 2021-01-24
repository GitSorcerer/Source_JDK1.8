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
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10, ((queue, task) -> {
//            queue.put(task);
            queue.put(task, 1500, TimeUnit.SECONDS);
        }));
        for (int i = 0; i < 5; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
//                    TimeUnit.SECONDS.sleep(1500);
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ":" + j);
            });
        }
    }
}
