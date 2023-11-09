package com.odk.base.dto.interfaces;

/**
 * BaseErrorCode
 *
 * @Description:
 * @CreateDate: 2023/11/9 21:14
 * @Version: 1.0
 * @Author: oubin
 */
public interface BaseErrorCode {

    /**
     * get error type
     * @return
     */
    String getErrorType();

    /**
     * get error code
     * @return
     */
    String getCode();

    /**
     * get error context
     * @return
     */
    String getErrorContext();
}
