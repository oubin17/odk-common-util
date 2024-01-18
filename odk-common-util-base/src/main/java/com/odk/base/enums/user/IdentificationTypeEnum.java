package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static IdentificationTypeEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (IdentificationTypeEnum identification : values()) {
            if (identification.code.equals(code)) {
                return identification;
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
