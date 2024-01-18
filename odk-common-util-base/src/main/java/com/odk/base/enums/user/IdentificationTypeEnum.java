package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;

/**
 * PasswordTypeEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
public enum IdentificationTypeEnum implements IEnum {

    PASSWORD("1", "PASSWORD"),

    SECURITY_QUESTION("2", "SECURITY_QUESTION")
    ;

    private final String code;

    private final String description;

    IdentificationTypeEnum(String code, String description) {
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
