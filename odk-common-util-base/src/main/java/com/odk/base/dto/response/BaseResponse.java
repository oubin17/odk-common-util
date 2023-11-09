package com.odk.base.dto.response;

import com.odk.base.dto.DTO;

/**
 * BaseResponse
 *
 * @Description:
 * @CreateDate: 2023/11/9 20:39
 * @Version: 1.0
 * @Author: oubin
 */
public class BaseResponse extends DTO {
    private static final long serialVersionUID = -3297973896762998365L;

    private boolean success;

    private String errorType;

    private String code;

    private String errorContext;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(String errorContext) {
        this.errorContext = errorContext;
    }
}
