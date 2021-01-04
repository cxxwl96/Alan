package com.alan.admin.system.validator;

import com.alan.modules.system.domain.Dept;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/30
 */
@Data
public class StudentValid implements Serializable {
    @NotNull(message = "请选择院系")
    private Dept collegeId;
    @NotNull(message = "请选择专业")
    private Dept specialtyId;
    @NotNull(message = "请选择年级")
    private Dept gradeId;
    @NotNull(message = "请选择班级")
    private Dept clazzId;
    @NotEmpty(message = "一寸半身照不能为空")
    private String photo;
    @NotEmpty(message = "学号不能为空")
    private String stuNo;
    @NotEmpty(message = "姓名不能为空")
    private String names;
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
    private String phone;
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
    @NotEmpty(message = "户籍所在地不能为空")
    private String domicile;
    @NotNull(message = "入学年月不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date admissionTime;
    @NotNull(message = "人学方式不能为空")
    private Byte anthropology;
    @NotNull(message = "就学方式不能为空")
    private Byte wayOfStudying;
    private Byte isEOS;
    @NotEmpty(message = "家庭地址不能为空")
    private String familyAddress;
    @NotEmpty(message = "现住址不能为空")
    private String currentAddress;
    @NotEmpty(message = "监护人姓名不能为空")
    private String guardianNames;
    @NotEmpty(message = "监护人电话不能为空")
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
    private String guardianPhone;
}