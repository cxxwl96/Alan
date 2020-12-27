package com.alan.component.actionLog.annotation;

import com.alan.component.actionLog.action.base.BaseActionMap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 行为日志注解
 * @author cxxwl96@sina.com
 * @date 2020/11/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActionLog {
    // 日志名称
    String name() default "";
    // 日志消息
    String message() default "";
    // 行为key
    String key() default "";
    // 行为类
    Class<? extends BaseActionMap> action() default BaseActionMap.class;
}
