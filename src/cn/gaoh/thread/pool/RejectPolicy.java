package cn.gaoh.thread.pool;

/**
 * @Description: 拒绝策略
 * @Author: gaoh
 * @Date: 2021/1/24 21:13
 * @Version: 1.0
 */
@FunctionalInterface
public interface RejectPolicy<T> {
    /**
     * 对外提供策略
     *
     * @param queue 队列
     * @param task  任务
     */
    void reject(BlockingQueue<T> queue, T task);
}
