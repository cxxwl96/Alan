package com.alan.admin.system.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/29
 */
@Data
public class StudentValid implements Serializable {
    @NotEmpty(message = "学号不能为空")
    private String stuNo;
    @NotEmpty(message = "姓名不能为空")
    private String names;
    @NotEmpty(message = "性别不能为空")
    private String sex;
    @NotEmpty(message = "出生年月不能为空")
    private String birthday;
    @NotEmpty(message = "证件号不能为空")
    private String IDNo;
    @NotEmpty(message = "民族不能为空")
    private String nation;
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
    private String guardianPhone;
}