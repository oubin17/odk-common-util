package com.odk.base.exception;

import com.odk.base.constant.ErrorTypes;
import com.odk.base.vo.interfaces.BaseErrorCode;
import org.apache.commons.lang3.StringUtils;

import static com.odk.base.constant.ErrorTypes.*;

/**
 * BizErrorCode
 *
 * @Description:
 * @CreateDate: 2023/11/9 21:12
 * @Version: 1.0
 * @Author: oubin
 */
public enum BizErrorCode implements BaseErrorCode {

    SUCCESS(SUCCEED, "000", "成功"),

    PARAM_ILLEGAL(BIZ, "001", "请求参数非法"),

    USER_HAS_EXISTED(BIZ, "010", "用户已经存在"),

    LOGIN_ID_DUPLICATE(BIZ, "011", "登录ID重复"),

    USER_NOT_EXIST(BIZ, "012", "用户不存在"),

    IDENTIFICATION_NOT_MATCH(BIZ, "013", "密码不匹配"),

    USER_STATUS_ERROR(BIZ, "014", "用户状态异常"),

    USER_NOT_LOGIN(BIZ, "015", "用户未登录"),

    IDENTIFICATION_SAME(BIZ, "016", "新旧密码一致"),

    SESSION_UNMATCHED(BIZ,"017", "Session不匹配"),

    TOKEN_EXPIRED(BIZ, "020", "Token过期"),

    TOKEN_MISSING(BIZ, "021", "Token缺失"),

    TOKEN_UNMATCHED(BIZ, "022", "Token不匹配"),

    PERMISSION_DENY(BIZ, "030", "暂无权限"),

    VERIFY_CODE_UNMATCHED(BIZ, "040", "验证码不匹配"),

    SYSTEM_ERROR(SYSTEM, "-100", "未知系统异常")

    ;

    private final String errorType;

    private final String errorCode;

    private final String errorContext;

    /**
     *
     * @param errorType {@link ErrorTypes}
     * @param errorCode error code
     * @param errorContext error context
     */
    BizErrorCode(String errorType, String errorCode, String errorContext) {
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.errorContext = errorContext;
    }

    /**
     * get error code by code
     * @param code code
     * @return
     */
    public static BizErrorCode getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (BizErrorCode errorCode : values()) {
            if (StringUtils.equalsIgnoreCase(errorCode.errorCode, code)) {
                return errorCode;
            }
        }
        return null;
    }
    /**
     * according code get name
     *
     * @param code code
     * @return corresponding name
     */
    public static String getNameByCode(String code) {

        BizErrorCode errorCode = getByCode(code);
        return (errorCode == null) ? "" : errorCode.name();
    }

    @Override
    public String getErrorType() {
        return errorType;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorContext() {
        return errorContext;
    }
}
