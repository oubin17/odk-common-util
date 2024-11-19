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

    SUCCESS(SUCCEED, "000", "success"),

    PARAM_ILLEGAL(BIZ, "001", "request param illegal"),


    USER_HAS_EXISTED(BIZ, "010", "user has existed"),

    LOGIN_ID_DUPLICATE(BIZ, "011", "login id duplicate"),
    USER_NOT_EXIST(BIZ, "012", "user not exist"),

    IDENTIFICATION_NOT_MATCH(BIZ, "013", "identification not match"),

    USER_STATUS_ERROR(BIZ, "014", "user status error"),

    TOKEN_EXPIRED(BIZ, "020", "session expired"),

    TOKEN_MISSING(BIZ, "021", "token missing"),

    TOKEN_UNMATCHED(BIZ, "O22", "token unmatched"),

    SYSTEM_ERROR(SYSTEM, "-100", "unknown system error")


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
