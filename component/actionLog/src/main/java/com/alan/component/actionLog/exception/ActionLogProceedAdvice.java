package com.alan.component.actionLog.exception;

import com.alan.common.exception.advice.ExceptionAdvice;
import com.alan.component.actionLog.action.SystemAction;
import com.alan.component.actionLog.annotation.ActionLog;

/**
 * 运行时抛出的异常进行日志记录
 * @author cxxwl96@sina.com
 * @date 2020/4/6
 */
public class ActionLogProceedAdvice implements ExceptionAdvice {

    @Override
    @ActionLog(key = SystemAction.RUNTIME_EXCEPTION, action = SystemAction.class)
    public void run(RuntimeException e) {}
}
