package com.odk.base.enums.cache;

import com.odk.base.enums.IEnum;

/**
 * CacheActionEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/8/5
 */
public enum CacheActionEnum  implements IEnum {

    ADD("ADD", "设置缓存"),
    UPDATE("UPDATE", "更新缓存"),
    DELETE("DELETE", "删除缓存"),
    ;

    private final String code;

    private final String description;


    CacheActionEnum(String code, String description) {
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
