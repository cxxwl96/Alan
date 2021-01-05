package com.alan.modules.system.domain;

import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.StatusUtil;
import com.alan.component.excel.annotation.Excel;
import com.alan.modules.system.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/05
 */
@Data
@Entity
@Table(name="sims_teacher")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class Teacher implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    // 系统用户
    private User userId;
    // 院系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    @JsonIgnore
    private Dept collegeId;
    // 教职工号
    private String no;
    // 职称
    @Excel(value = "职称", dict = "PROFESSIONAL_TITLE")
    private Byte professionalTitle;
    // 学位
    @Excel(value = "学位", dict = "ACADEMIC_DEGREE")
    private Byte academicDegree;
    // 照片
    private String photo;
    // 教师名称
    private String names;
    // 现任职务
    private String post;
    // 联系电话
    private String phone;
    // 电子信箱
    private String email;
    // 性别
    @Excel(value = "性别", dict = "USER_SEX")
    private Byte sex;
    // 出生年月
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    // 身份证类型
    @Excel(value = "身份证类型", dict = "ID_TYPE")
    private Byte idType;
    // 证件号
    private String idNo;
    // 民族
    @Excel(value = "民族", dict = "NATION")
    private Byte nation;
    // 入校年月
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enrollmentTime;
    // 补充信息
    private String additionalInfo;
    // 备注
    private String remark;
    // 创建时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
    // 创建者
    @CreatedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="create_by")
    @JsonIgnore
    private User createBy;
    // 更新者
    @LastModifiedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="update_by")
    @JsonIgnore
    private User updateBy;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();
}