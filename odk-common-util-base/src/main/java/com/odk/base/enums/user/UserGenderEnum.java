package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * UserGenderEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/8/4
 */
public enum UserGenderEnum implements IEnum {

    MALE("1", "MALE"),

    FEMALE("2", "FEMALE"),

    UNKNOWN("3", "UNKNOWN");

    private final String code;

    private final String description;


    UserGenderEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static UserGenderEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (UserGenderEnum genderEnum : values()) {
            if (genderEnum.code.equals(code)) {
                return genderEnum;
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
