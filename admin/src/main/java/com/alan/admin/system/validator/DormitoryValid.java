package com.alan.admin.system.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/04
 */
@Data
public class DormitoryValid implements Serializable {
    @NotEmpty(message = "宿舍类型不能为空")
    private String type;
    @NotEmpty(message = "宿舍楼不能为空")
    private String building;
    @NotEmpty(message = "宿舍名不能为空")
    private String name;
}