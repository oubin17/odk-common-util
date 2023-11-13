package com.odk.databasespringbootstarter;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * DatabaseTypeEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/12
 */
@Getter
public enum DatabaseTypeEnum {

    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),

    ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver");

    private final String type;

    private final String driverClassName;

    DatabaseTypeEnum(String type, String driverClassName) {
        this.type = type;
        this.driverClassName = driverClassName;
    }

    /**
     * 返回数据库类型对应的驱动
     *
     * @param type
     * @return
     */
    public static DatabaseTypeEnum getByType(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        for (DatabaseTypeEnum databaseTypeEnum: values()) {
            if (StringUtils.equalsIgnoreCase(databaseTypeEnum.type, type)) {
                return databaseTypeEnum;
            }
        }
        return null;
    }
}
