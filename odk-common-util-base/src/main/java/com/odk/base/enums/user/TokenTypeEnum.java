package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static TokenTypeEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (TokenTypeEnum tokenType : values()) {
            if (tokenType.code.equals(code)) {
                return tokenType;
            }
        }
        return null;
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
