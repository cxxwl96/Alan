package com.alan.admin.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author cxxwl96@sina.com
 * @date 2020/8/14
 */
@Data
public class RoleValid implements Serializable {
    @NotEmpty(message = "角色编号不能为空")
    private String name;
    @NotEmpty(message = "角色名称不能为空")
    private String title;
}
