package com.odk.redisspringbootstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * CacheableDataService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/8/5
 */
@Component
public class CacheableDataService {

    private static final Logger log = LoggerFactory.getLogger(CacheableDataService.class);


    private DistributedLockService distributedLockService;

    private RedisUtil redisUtil;

    /**
     * 访问缓存数据，如不存在，写缓存带默认过期时间
     *
     * @param redisKey
     * @param databaseGetter
     * @return
     * @param <T>
     */
    public <T> T getOrCreate(String redisKey, String lockKey, Supplier<T> databaseGetter) {
        return getOrCreate(
                redisKey,
                lockKey,
                1L,
                3L,
                Duration.ofDays(30),
                databaseGetter

        );
    }


    /**
     * 访问缓存数据，如不存在，写缓存带默认过期时间
     *
     * @param redisKey
     * @param databaseGetter
     * @return
     * @param <T>
     */
    public <T> T getOrCreate(String redisKey, String lockKey, Duration duration, Supplier<T> databaseGetter) {
        return getOrCreate(
                redisKey,
                lockKey,
                1L,
                3L,
                duration,
                databaseGetter
        );
    }



    /**
     * 设置缓存，无过期时间
     *
     * @param redisKey
     * @param lockKey
     * @param databaseGetter
     * @return
     * @param <T>
     */
    public <T> T getOrCreateNoExpire(String redisKey, String lockKey, Supplier<T> databaseGetter) {
        return getOrCreate(
                redisKey,
                lockKey,
                1L,
                3L,
                Duration.ZERO,
                databaseGetter
        );
    }

    /**
     * 使用分布式锁防止缓存击穿
     *
     * @param redisKey
     * @param waitTime
     * @param leaseTime
     * @param databaseGetter
     * @param duration
     * @return
     * @param <T>
     */
    public <T> T getOrCreate(String redisKey,
                             String lockKey,
                             long waitTime,
                             long leaseTime,
                             Duration duration,
                             Supplier<T> databaseGetter) {
        // 1. 首先尝试从缓存中获取
        Object value = redisUtil.get(redisKey);
        if (null != value) {
            log.info("Cache Hit, Key={}", redisKey);
            return (T) value;
        }
        log.info("Cache Miss, Key={}", redisKey);

        // 2. 使用分布式锁防止缓存击穿
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            boolean acquired = distributedLockService.tryLock(lockKey, waitTime, leaseTime);
            if (acquired) {
                try {
                    // 双重检查缓存
                    value = redisUtil.get(redisKey);
                    if (value != null) {
                        return (T) value;
                    }

                    // 查询数据库
                    T fromDatabase = databaseGetter.get();
                    if (fromDatabase != null) {
                        // 写入缓存
                        if (duration.isZero() || duration.isNegative()) {
                            redisUtil.set(redisKey, fromDatabase);
                        } else {
                            // 将 Duration 转换为秒或毫秒
                            redisUtil.set(redisKey, fromDatabase, duration.getSeconds(), TimeUnit.SECONDS);
                            // 或者如果需要更高精度:
                            // redisUtil.set(redisKey, fromDatabase, duration.toMillis(), TimeUnit.MILLISECONDS);
                        }
                        return fromDatabase;
                    }
                    return null;
                } finally {
                    distributedLockService.unlock(lockKey);
                }
            } else {
                retryCount++;
                if (retryCount < maxRetries) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        // 降级处理：直接查询数据库
        T fromDatabase = databaseGetter.get();
        if (fromDatabase != null) {
            // 写入缓存
            if (duration.isZero() || duration.isNegative()) {
                redisUtil.set(redisKey, fromDatabase);
            } else {
                redisUtil.set(redisKey, fromDatabase, duration.getSeconds(), TimeUnit.SECONDS);
            }
            return fromDatabase;
        }
        return null;
    }



//    public <T> T getOrCreate(String redisKey, String dbKey, Function<String, T> databaseGetter) {
//        return getOrCreate(
//                redisKey,
//                1L,
//                3L,
//                dbKey,
//                key -> {
//                    Object value = redisUtil.get(key);
//                    return Optional.ofNullable((T) value);
//                }, // 使用新增的泛型方法
//                databaseGetter.andThen(Optional::ofNullable),
//                data -> redisUtil.set(redisKey, data, 30, TimeUnit.DAYS));
//    }
//
//    public <T> T getOrCreate(String redisKey, String dbKey, Function<String, T> databaseGetter, long timeout, TimeUnit unit) {
//        return getOrCreate(
//                redisKey,
//                1L,
//                3L,
//                dbKey,
//                key -> {
//                    Object value = redisUtil.get(key);
//                    return Optional.ofNullable((T) value);
//                }, // 使用新增的泛型方法
//                databaseGetter.andThen(Optional::ofNullable),
//                data -> redisUtil.set(redisKey, data, timeout, unit));
//    }

//
//    /**
//     * 缓存数据
//     *
//     * @param redisKey
//     * @param waitTime
//     * @param leaseTime
//     * @param dbKey
//     * @param cacheGetter
//     * @param databaseGetter
//     * @param cachePutter
//     * @return
//     * @param <T>
//     */
//    public <T> T getOrCreate(String redisKey, long waitTime, long leaseTime, String dbKey,
//                             Function<String, Optional<T>> cacheGetter,
//                             Function<String, Optional<T>> databaseGetter,
//                             Consumer<T> cachePutter) {
//
//        // 1. 首先尝试从缓存中获取
//        Optional<T> cached = cacheGetter.apply(redisKey);
//        if (cached.isPresent()) {
//            return cached.get();
//        }
//
//        // 2. 使用分布式锁防止缓存击穿
//        int maxRetries = 3;
//        int retryCount = 0;
//
//        while (retryCount < maxRetries) {
//            boolean acquired = distributedLockService.tryLock(redisKey, waitTime, leaseTime);
//            if (acquired) {
//                try {
//                    // 双重检查缓存
//                    cached = cacheGetter.apply(dbKey);
//                    if (cached.isPresent()) {
//                        return cached.get();
//                    }
//
//                    // 查询数据库
//                    Optional<T> fromDatabase = databaseGetter.apply(dbKey);
//                    if (fromDatabase.isPresent()) {
//                        T result = fromDatabase.get();
//                        // 写入缓存
//                        cachePutter.accept(result);
//                        return result;
//                    }
//                    return null;
//                } finally {
//                    distributedLockService.unlock(redisKey);
//                }
//            } else {
//                retryCount++;
//                if (retryCount < maxRetries) {
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        break;
//                    }
//                }
//            }
//        }
//
//        // 降级处理：直接查询数据库
//        Optional<T> fromDatabase = databaseGetter.apply(dbKey);
//        fromDatabase.ifPresent(cachePutter);
//        return fromDatabase.orElse(null);
//    }


    @Autowired
    public void setDistributedLockService(DistributedLockService distributedLockService) {
        this.distributedLockService = distributedLockService;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
