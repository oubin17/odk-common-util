package com.odk.base.vo.response;

import com.odk.base.exception.BizErrorCode;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * ServiceResponse
 *
 * @Description:
 * @CreateDate: 2023/11/9 21:00
 * @Version: 1.0
 * @Author: oubin
 */
public class ServiceResponse<T> extends BaseResponse {

    @Serial
    private static final long serialVersionUID = -6642539633697397474L;

    /**
     * 系统业务数据返回
     */
    private T data;

    /**
     * 允许系统在出现错误时，返回额外信息给到调用方。
     */
    private Map<String, Object> extendInfo;

    private ServiceResponse() {
        super();
    }

    public static <T> ServiceResponse<T> valueOfSuccess(T value) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.data = value;
        response.setSuccess(true);
        response.setErrorType(BizErrorCode.SUCCESS.getErrorType());
        response.setErrorCode(BizErrorCode.SUCCESS.getErrorCode());
        response.setErrorContext(null);
        return response;
    }

    public static <T> ServiceResponse<T> valueOfSuccess() {
        return valueOfSuccess(null);
    }

    public static <T> ServiceResponse<T> valueOfError(BizErrorCode resultCode) {

        return valueOfError(resultCode, resultCode.getErrorContext());
    }

    public static <T> ServiceResponse<T> valueOfError(BizErrorCode resultCode, String errorContext) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setErrorType(resultCode.getErrorType());
        response.setErrorCode(resultCode.getErrorCode());
        response.setErrorContext(errorContext);
        return response;
    }

    public static <T> ServiceResponse<T> valueOfError(String errorType, String errorCode, String errorContext) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setErrorType(errorType);
        response.setErrorCode(errorCode);
        response.setErrorContext(errorContext);
        return response;
    }

    public static <T> ServiceResponse<T> valueOfError(String errorType, String errorCode, String errorContext,  Map<String, Object> extendInfo) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setErrorType(errorType);
        response.setErrorCode(errorCode);
        response.setErrorContext(errorContext);
        response.setExtendInfo(extendInfo);
        return response;
    }

    public static <T> ServiceResponse<T> valueOf(ServiceResponse preResponse) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(preResponse.isSuccess());
        response.setErrorType(preResponse.getErrorType());
        response.setErrorCode(preResponse.getErrorCode());
        response.setErrorContext(preResponse.getErrorContext());
        return response;
    }



    /**
     *  add the returned extension information
     *
     * @param key  extended information key
     * @param obj  extended information
     */
    public void putExtendInfo(String key, Object obj) {
        if (null == extendInfo) {
            extendInfo = new HashMap<>();
        }

        extendInfo.put(key, obj);
    }

    /**
     *  obtain extended information
     *
     * @param key  extended information key
     * @return  extended information
     */
    public Object fetchExtendInfoByKey(String key) {
        if (null == extendInfo) {
            return null;
        }
        return extendInfo.get(key);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }
}
