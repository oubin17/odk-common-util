package com.odk.base.idgenerator;

import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;

/**
 * SnowflakeIdUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/27
 */
public class SnowflakeIdUtil {

    // 基准时间戳（可以自定义，不能随意修改）
    private static final long EPOCH = 1672531200000L; // 2023-01-01 00:00:00

    // 各部分的位数
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    // 每部分的最大值
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    // 各部分的位移
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    // 序列号的掩码
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private static long workerId;
    private static long datacenterId;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    /**
     * 初始化 Worker ID 和 Datacenter ID
     *
     * @param workerId     工作节点 ID (0-31)
     * @param datacenterId 数据中心 ID (0-31)
     */
    public static void init(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new BizException(BizErrorCode.SYSTEM_ERROR,
                    String.format("Worker ID must be between 0 and %d", MAX_WORKER_ID)
            );
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new BizException(BizErrorCode.SYSTEM_ERROR,
                    String.format("Datacenter ID must be between 0 and %d", MAX_DATACENTER_ID)
            );
        }
        SnowflakeIdUtil.workerId = workerId;
        SnowflakeIdUtil.datacenterId = datacenterId;
    }

    /**
     * 生成唯一 ID
     *
     * @return 唯一 ID
     */
    public static synchronized long nextId() {
        long timestamp = currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (timestamp == lastTimestamp) {
            // 同一毫秒内生成 ID
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 序列号耗尽，等待下一毫秒
                timestamp = waitUntilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒，重置序列号
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 拼接 ID
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT) |
                (datacenterId << DATACENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    private static long waitUntilNextMillis(long lastTimestamp) {
        long timestamp = currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis();
        }
        return timestamp;
    }

    private static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
