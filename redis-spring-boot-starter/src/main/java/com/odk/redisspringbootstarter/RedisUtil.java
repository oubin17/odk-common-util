package com.odk.redisspringbootstarter;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisTemplateUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/11
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;


    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置指定 key 的值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值并设置过期时间
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取指定 key 的值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取指定key的值，并转换为指定类型
     * @param key Redis键
     * @param clazz 返回值的类型Class
     * @return 指定类型的值，如果不存在则返回null
     * @param <T> 泛型类型
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = get(key);
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }

    /**
     * 获取指定key的值，并返回Optional包装的指定类型
     * @param key Redis键
     * @param clazz 返回值的类型Class
     * @return Optional包装的指定类型值
     * @param <T> 泛型类型
     */
    public <T> Optional<T> getOptional(String key, Class<T> clazz) {
        return Optional.ofNullable(get(key, clazz));
    }

    /**
     * 删除指定 key
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 判断 key 是否存在
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 设置 key 的过期时间
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取指定 key 的剩余过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 设置值（仅当 key 不存在时有效）
     * 相当于 Redis 的 SETNX 命令
     */
    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return Boolean.TRUE.equals(operations.setIfAbsent(key, value, timeout, unit));
    }

    /**
     * 对指定 key 的值进行递增（适用于整数值）
     * @param key 键
     * @return 递增后的值（当key不存在时会初始化为0后执行操作）
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1L);
    }

    /**
     * 对指定 key 的值进行递增（适用于整数值）
     * @param key 键
     * @param delta 递增因子（必须>=0）
     * @return 递增后的值（当key不存在时会初始化为0后执行操作）
     */
    public Long incrBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Hash - 设置哈希表的值
     */
    public void hmSet(String key, Map<String, Object> hashValues) {
        redisTemplate.opsForHash().putAll(key, hashValues);
    }

    /**
     * Hash - 获取哈希表中的值
     */
    public Object hmGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * Hash - 获取整个哈希表
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * List - 左侧推入元素
     */
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * List - 右侧弹出元素
     */
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * List - 获取列表的所有元素
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Set - 添加元素到集合
     */
    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Set - 获取集合中的所有元素
     */
    public Object sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Set - 判断元素是否在集合中
     */
    public boolean sIsMember(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * Sorted Set - 添加元素到有序集合
     */
    public boolean zAdd(String key, Object value, double score) {
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
    }

    /**
     * Sorted Set - 获取有序集合中的元素
     */
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }
}
