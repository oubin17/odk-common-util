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
//        dataSource.setInitialSize();
        dataSource.setMaxActive(5);
        //...
        LOGGER.info("init datasource end");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }
}
