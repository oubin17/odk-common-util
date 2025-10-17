package com.odk.databasespringbootstarter;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * DataSourceChoose
 * 在定时任务中，jpa 未生效：
 * 配置覆盖了 Spring Boot 为 JPA 自动配置的 JpaTransactionManager。由于 DataSourceTransactionManager 并非专为 JPA 设计，它可能无法在定时任务等特定场景下正确地将数据库连接（Connection）与 JPA 的 EntityManager 进行同步，导致 save 操作实际上并未与事务关联。
 * 最直接有效的解决方案是：移除您手动配置的 DataSourceTransactionManager 和 TransactionTemplate Bean。
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/12
 */
@SpringBootConfiguration
public class DataSourceSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceSelector.class);

    private final DatabaseConfiguration databaseConfiguration;

    public DataSourceSelector(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    @Bean
    public DataSource dataSource() {
        LOGGER.info("begin init datasource");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(databaseConfiguration.getDriver());
        dataSource.setUrl(databaseConfiguration.getUrl());
        dataSource.setUsername(databaseConfiguration.getUser());
        dataSource.setPassword(databaseConfiguration.getPassword());
        //连接池的其他的属性。。。
        dataSource.setName("DataSource-Druid");
        dataSource.setInitialSize(databaseConfiguration.getInitialSize());
        dataSource.setMinIdle(databaseConfiguration.getMinIdle());
        dataSource.setMaxActive(databaseConfiguration.getMaxActive());
        dataSource.setMaxWait(databaseConfiguration.getMaxWait());
        dataSource.setPoolPreparedStatements(databaseConfiguration.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(databaseConfiguration.getMaxPoolPreparedStatementPreConnectionSize());
        dataSource.setValidationQuery(databaseConfiguration.getValidationQuery());
        dataSource.setValidationQueryTimeout(databaseConfiguration.getValidationQueryTimeout());
        dataSource.setTestOnBorrow(databaseConfiguration.isTestOnBorrow());
        dataSource.setTestOnReturn(databaseConfiguration.isTestOnReturn());
        dataSource.setTestWhileIdle(databaseConfiguration.isTestWhileIdle());
        dataSource.setTimeBetweenEvictionRunsMillis(databaseConfiguration.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(databaseConfiguration.getMinEvictableIdleTImeMillis());
        dataSource.setMaxEvictableIdleTimeMillis(databaseConfiguration.getMaxEvictableIdleTimeMillis());
        dataSource.setUseGlobalDataSourceStat(databaseConfiguration.isUserGlobalDataSourceStat());
//        dataSource.setConnectProperties(databaseConfiguration.getConnectionProperties());
        return dataSource;
    }

    /**
     * jdbctemplate 操作模板
     *
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    /**
     * 添加事务管理器
     *
     * @return
     */

//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        // 使用专为JPA设计的事务管理器
//        return new JpaTransactionManager(entityManagerFactory);
//    }


//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        // 使用专为JPA设计的事务管理器
//        return new JpaTransactionManager(entityManagerFactory);
//    }


//    @Bean
//    public TransactionTemplate transactionTemplate() {
//        TransactionTemplate transactionTemplate = new TransactionTemplate();
//        transactionTemplate.setTransactionManager(transactionManager());
//        return transactionTemplate;
//    }
}
