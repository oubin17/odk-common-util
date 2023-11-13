package com.odk.databasespringbootstarter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;

/**
 * DatabaseConfiguration
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/12
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
}
