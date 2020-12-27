package com.alan.common.exception;

import com.alan.common.enums.ResultEnum;

/**
 * 自定义异常对象{统一异常处理：失败}
 * @author cxxwl96@sina.com
 * @date 2020/10/17
 */
public class ResultExceptionError extends ResultException {

    /**
     * 统一异常处理：抛出默认失败信息
     */
    public ResultExceptionError() {
        super(ResultEnum.ERROR);
    }

    /**
     * 统一异常处理：抛出失败提示信息
     * @param message 提示信息
     */
    public ResultExceptionError(String message) {
        super(ResultEnum.ERROR.getCode(), message);
    }
}
