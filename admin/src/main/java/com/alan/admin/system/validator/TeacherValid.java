package com.alan.admin.system.validator;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/05
 */
@Data
public class TeacherValid implements Serializable {
    @NotNull(message = "院系不能为空")
    private Object collegeId;
    @NotEmpty(message = "教职工号不能为空")
    private String no;
    @NotNull(message = "职称不能为空")
    private Byte professionalTitle;
    @NotNull(message = "学位不能为空")
    private Byte academicDegree;
    @NotEmpty(message = "照片不能为空")
    private String photo;
    @NotEmpty(message = "教师名称不能为空")
    private String names;
    @NotEmpty(message = "联系电话不能为空")
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
    private String phone;
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotNull(message = "性别不能为空")
    private Byte sex;
    @NotNull(message = "出生年月不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    @NotNull(message = "身份证类型不能为空")
    private Byte idType;
    @NotEmpty(message = "证件号不能为空")
    private String idNo;
    @NotNull(message = "民族不能为空")
    private Byte nation;
    @NotNull(message = "入校年月不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enrollmentTime;
}