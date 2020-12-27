package com.alan.component.actionLog.action.model;

import com.alan.modules.system.enums.ActionLogEnum;
import lombok.Getter;

/**
 * @author cxxwl96@sina.com
 * @date 2020/10/15
 */
@Getter
public class SystemType extends BusinessType{

    /** 日志类型 */
    protected Byte type = ActionLogEnum.SYSTEM.getCode();

    public SystemType(String message) {
        super(message);
    }

    public SystemType(String name, String message) {
        super(name, message);
    }
}
