package com.odk.base.exception;


import org.apache.commons.lang3.StringUtils;

/**
 * AssertUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public class AssertUtil {

    private AssertUtil() {
    }

    public static void notNull(Object object, BizErrorCode errorCode) {
        if (object == null) {
            throw new BizException(errorCode);
        }
    }

    public static void notNull(Object object, BizErrorCode errorCode, String message) {
        if (object == null) {
            throw new BizException(errorCode, message);
        }
    }

    public static void isNull(Object object, BizErrorCode errorCode, String message) {
        if (object != null) {
            throw new BizException(errorCode, message);
        }
    }

    public static void isNull(Object object, BizErrorCode errorCode) {
        if (object != null) {
            throw new BizException(errorCode);
        }
    }

    public static void isTrue(boolean expression, BizErrorCode errorCode, String message) {
        if (!expression) {
            throw new BizException(errorCode, message);
        }
    }

    public static void isTrue(boolean expression, BizErrorCode errorCode) {
        if (!expression) {
            throw new BizException(errorCode);
        }
    }


    public static void isFalse(boolean expression, BizErrorCode errorCode, String message) {
        if (expression) {
            throw new BizException(errorCode, message);
        }
    }

    public static void isFalse(boolean expression, BizErrorCode errorCode) {
        if (expression) {
            throw new BizException(errorCode);
        }
    }

    public static void isNotEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BizException(BizErrorCode.PARAM_ILLEGAL, message);
        }
    }

    public static void isNotEmpty(String str, BizErrorCode errorCode, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BizException(errorCode, message);
        }
    }

    public static void isEmpty(String str, BizErrorCode errorCode, String message) {
        if (StringUtils.isNotEmpty(str)) {
            throw new BizException(errorCode, message);
        }
    }
}
