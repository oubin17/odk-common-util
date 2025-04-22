package com.odk.redisspringbootstarter;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * DistributedLockService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/4/22
 */
@Component
public class DistributedLockService {

    private final RedissonClient redissonClient;

    public DistributedLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 获取锁（阻塞直到成功）
     * @param lockKey 锁名称
     * @param leaseTime 锁自动释放时间（单位：秒）
     */
    public void lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁（非阻塞）
     * @param lockKey 锁名称
     * @param waitTime 最大等待时间（单位：秒）
     * @param leaseTime 锁自动释放时间（单位：秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 释放锁
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * 检查锁是否存在
     */
    public boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }
}
