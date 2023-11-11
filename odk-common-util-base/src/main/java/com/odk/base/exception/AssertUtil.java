package com.odk.base.exception;


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
}
