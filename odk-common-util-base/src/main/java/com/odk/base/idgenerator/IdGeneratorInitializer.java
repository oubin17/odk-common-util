package com.odk.base.idgenerator;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * IdGeneratorInitializer
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/27
 */
@Component
public class IdGeneratorInitializer {

    @Value("${snowflake.worker-id}")
    private long workerId;
    @Value("${snowflake.datacenter-id}")
    private long datacenterId;

    @PostConstruct
    public void init() {
        // 假设 Worker ID 和 Datacenter ID 配置为 1 和 1
        SnowflakeIdUtil.init(workerId, datacenterId);
    }
}
