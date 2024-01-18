package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;

/**
 * TokenTypeEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
public enum TokenTypeEnum implements IEnum {

    MOBILE("1", "MOBILE"),

    EMAIL("2", "EMAIL")

    ;

    private final String code;

    private final String description;


    TokenTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
