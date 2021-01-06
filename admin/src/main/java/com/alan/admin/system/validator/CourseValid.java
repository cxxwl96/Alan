package com.alan.admin.system.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/06
 */
@Data
public class CourseValid implements Serializable {
    @NotNull(message = "课程类型不能为空")
    private Byte type;
    @NotEmpty(message = "课程名称不能为空")
    private String names;
    @NotNull(message = "课程学时不能为空")
    private Double hour;
}