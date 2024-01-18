package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * UserTypeEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
public enum UserTypeEnum implements IEnum {

    INDIVIDUAL("1", "个人"),

    INST("2", "机构")
    ;


    private final String code;

    private final String description;

    UserTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static UserTypeEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (UserTypeEnum userType : values()) {
            if (userType.code.equals(code)) {
                return userType;
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
