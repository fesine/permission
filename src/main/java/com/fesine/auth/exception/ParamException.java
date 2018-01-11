package com.fesine.auth.exception;

/**
 * @description: 参数校验异常
 * @author: Fesine
 * @createTime:2018/1/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/11
 */
public class ParamException extends RuntimeException {
    private static final long serialVersionUID = -4826475494235117855L;

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
