package com.alan.admin.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author cxxwl96@sina.com
 * @date 2020/10/19
 */
@Data
public class ActionLogValid implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;
}
