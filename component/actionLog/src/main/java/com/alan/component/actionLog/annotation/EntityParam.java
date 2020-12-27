package com.alan.component.actionLog.annotation;

import java.lang.annotation.*;

/**
 * 控制器实体参数注解
 * @author cxxwl96@sina.com
 * @date 2020/2/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface EntityParam {
}
