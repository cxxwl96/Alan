package com.alan.admin.system.validator;

import com.alan.modules.system.domain.Course;
import com.alan.modules.system.domain.Student;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/06
 */
@Data
public class ScoreValid implements Serializable {
    @NotEmpty(message = "学年不能为空")
    private String academicYear;
    @NotNull(message = "学期不能为空")
    private Byte semester;
    @NotNull(message = "学生不能为空")
    private Student studentId;
    @NotNull(message = "课程不能为空")
    private Course courseId;
    @NotNull(message = "分数不能为空")
    @Digits(integer = 12, fraction = 2, message = "分数只保留小数点后两位")
    private Double fraction;
}