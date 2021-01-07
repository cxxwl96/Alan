package com.alan.admin.system.validator;

import com.alan.modules.system.domain.Course;
import com.alan.modules.system.domain.Dept;
import com.alan.modules.system.domain.Teacher;
import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/07
 */
@Data
public class TeachingPlanValid implements Serializable {
    @NotEmpty(message = "学年不能为空")
    private String academicYear;
    @NotNull(message = "学期不能为空")
    private Byte semester;
    @NotNull(message = "院系不能为空")
    private Dept collegeId;
    @NotNull(message = "专业不能为空")
    private Dept specialtyId;
    @NotNull(message = "年级不能为空")
    private Dept gradeId;
    @NotNull(message = "班级不能为空")
    private Dept clazzId;
    @NotNull(message = "课程不能为空")
    private Course courseId;
    @NotNull(message = "教师不能为空")
    private Teacher teacherId;
    @NotNull(message = "周不能为空")
    private Byte weeks;
    @NotNull(message = "时间段不能为空")
    private Byte periodOfTime;
    @NotEmpty(message = "开始时间不能为空")
    private String startTime;
    @NotEmpty(message = "结束时间不能为空")
    private String endTime;
}