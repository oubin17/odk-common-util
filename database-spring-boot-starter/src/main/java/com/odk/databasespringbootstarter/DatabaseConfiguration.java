package com.odk.databasespringbootstarter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;

/**
 * DatabaseConfiguration
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/12b
 */
@Data
@SpringBootConfiguration
public class DatabaseConfiguration {

    @Value("${spring.odk.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.odk.datasource.url}")
    private String url;

    @Value("${spring.odk.datasource.username}")
    private String user;

    @Value("${spring.odk.datasource.password}")
    private String password;

    @Value("${spring.druid.initial-size}")
    private Integer initialSize;

    @Value("${spring.druid.min-idle}")
    private Integer minIdle;

    @Value("${spring.druid.max-active}")
    private Integer maxActive;

    @Value("${spring.druid.max-wait}")
    private Integer maxWait;

    @Value("${spring.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.druid.max-pool-prepared-statement-per-connection-size}")
    private Integer maxPoolPreparedStatementPreConnectionSize;

    @Value("${spring.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.druid.validation-query-timeout}")
    private Integer validationQueryTimeout;

    @Value("${spring.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.druid.test-on-return}")
    private boolean testOnReturn;

    @Value("${spring.druid.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${spring.druid.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${spring.druid.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTImeMillis;

    @Value("${spring.druid.max-evictable-idle-time-millis}")
    private Integer maxEvictableIdleTimeMillis;

    @Value("${spring.druid.use-global-data-source-stat}")
    private boolean userGlobalDataSourceStat;

    @Value("${spring.druid.connection-properties}")
    private String connectionProperties;


}
