package com.odk.redisspringbootstarter;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(DistributedLockService.class);

    private final RedissonClient redissonClient;

    public DistributedLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 获取锁（阻塞直到成功）
     *
     * @param lockKey   锁名称
     * @param leaseTime 锁自动释放时间（单位：秒）
     */
    public void lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
    }

    // 新增支持自动续期的方法（默认30秒看门狗）
    public void lockWithWatchdog(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(); // 不指定leaseTime启用自动续期
    }

    // 带时间单位的通用方法
    public boolean tryLock(String lockKey, long waitTime, TimeUnit unit) {
        log.info("尝试获取锁 {} [thread: {}]", lockKey, Thread.currentThread().getName());
        try {
            return redissonClient.getLock(lockKey)
                    .tryLock(waitTime, unit.toSeconds(waitTime) * 2, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }


    /**
     * 尝试获取锁（非阻塞）
     *
     * @param lockKey   锁名称
     * @param waitTime  最大等待时间（单位：秒）
     * @param leaseTime 锁自动释放时间（单位：秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        log.info("尝试获取锁 {} [thread: {}]", lockKey, Thread.currentThread().getName());
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
        try {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("释放锁成功 {} [thread: {}]", lockKey, Thread.currentThread().getName());

            }
        } catch (IllegalMonitorStateException e) {
            log.warn("尝试释放未持有的锁: {}", lockKey);
        }
    }

    /**
     * 检查锁是否存在
     */
    public boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }

    //功能增强
    // 获取公平锁
    public RLock getFairLock(String lockKey) {
        return redissonClient.getFairLock(lockKey);
    }


}
