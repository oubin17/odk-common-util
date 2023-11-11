package com.odk.base.exception;

/**
 * BizException
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = 5913727621719541968L;

    private BizErrorCode errorCode;

    public BizException(String errMessage) {
        super(errMessage);
    }

    public BizException(BizErrorCode errorCode) {
        super(errorCode.getErrorContext());
    }

    public BizException(BizErrorCode errorCode, String errMessage) {
        super(errMessage);
        this.errorCode = errorCode;
    }


    public BizErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(BizErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
