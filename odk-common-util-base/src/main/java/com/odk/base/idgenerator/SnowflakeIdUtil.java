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
import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeIdUtil {

    private static final long EPOCH = 1672531200000L; // 2023-01-01 00:00:00
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private static long workerId;
    private static long datacenterId;

    private static final AtomicLong sequence = new AtomicLong(0);
    private static volatile long lastTimestamp = -1L;

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

    public static long nextId() {
        long timestamp = currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (timestamp == lastTimestamp) {
            long currentSequence = sequence.incrementAndGet() & SEQUENCE_MASK;
            if (currentSequence == 0) {
                // 序列号耗尽，等待下一毫秒
                timestamp = waitUntilNextMillis(lastTimestamp);
            }
        } else {
            sequence.set(0);
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT) |
                (datacenterId << DATACENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence.get();
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
