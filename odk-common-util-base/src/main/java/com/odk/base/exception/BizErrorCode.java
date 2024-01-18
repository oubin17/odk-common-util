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


    SYSTEM_ERROR(SYSTEM, "-100", "unknown system error")


    ;

    private final String errorType;

    private final String code;

    private final String errorContext;

    /**
     *
     * @param errorType {@link ErrorTypes}
     * @param code error code
     * @param errorContext error context
     */
    BizErrorCode(String errorType, String code, String errorContext) {
        this.errorType = errorType;
        this.code = code;
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
            if (StringUtils.equalsIgnoreCase(errorCode.code, code)) {
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
    public String getCode() {
        return code;
    }

    @Override
    public String getErrorContext() {
        return errorContext;
    }
}
