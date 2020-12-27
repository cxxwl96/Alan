package com.alan.admin.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/02
 */
@Data
public class DeptValid implements Serializable {
	@NotEmpty(message = "部门名称不能为空")
	private String title;
    @NotEmpty(message = "部门层级不能为空")
    private String level;
    @NotNull(message = "父级部门不能为空")
    private Long pid;
}
