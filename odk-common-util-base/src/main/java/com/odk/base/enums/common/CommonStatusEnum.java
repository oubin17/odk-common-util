package com.odk.base.enums.common;

import com.odk.base.enums.IEnum;

/**
 * CommonStatusEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
public enum CommonStatusEnum implements IEnum {

    NORMAL("0", "正常"),

    DELETE("1", "删除"),

    ;


    private final String code;

    private final String description;

    CommonStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    ;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
