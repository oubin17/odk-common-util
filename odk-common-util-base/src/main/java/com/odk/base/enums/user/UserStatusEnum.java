package com.odk.base.enums.user;

import com.odk.base.enums.IEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * UserStatusEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
public enum UserStatusEnum implements IEnum {

    NORMAL("1", "正常"),

    FROZEN("2", "冻结"),

    CLOSED("-1", "销户")
    ;


    private final String code;

    private final String description;

    UserStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static UserStatusEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (UserStatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
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
