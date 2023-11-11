package com.odk.base.exception;

/**
 * BaseException
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1975753863609858663L;

    protected BaseException(){}

    protected BaseException(String errMessage) {
        super(errMessage);
    }

    protected BaseException(String errMessage, Throwable e) {
        super(errMessage, e);
    }


}
