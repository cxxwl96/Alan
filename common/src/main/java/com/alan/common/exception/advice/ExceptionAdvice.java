package com.alan.common.exception.advice;

/**
 * 异常通知器接口
 * @author cxxwl96@sina.com
 * @date 2020/4/5
 */
public interface ExceptionAdvice {

    /**
     * 运行
     * @param e 异常对象
     */
    void run(RuntimeException e);
}
