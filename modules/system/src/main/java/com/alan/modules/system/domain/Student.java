package com.alan.modules.system.domain;

import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.StatusUtil;
import com.alan.component.excel.annotation.Excel;
import com.alan.modules.system.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @date 2020/12/30
 */
@Data
@Entity
@Table(name="sims_student")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Student implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    // 系统用户
    private User userId;
    // 学号
    private String stuNo;
    // 学籍号
    private String stuNumber;
    // 姓名
    private String names;
    // 曾用名
    private String formerNames;
    // 手机号
    private String phone;
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
    // 户籍所在地
    private String domicile;
    // 入学年月
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date admissionTime;
    // 人学方式
    @Excel(value = "人学方式", dict = "ANTHROPOLOGY")
    private Byte anthropology;
    // 就学方式
    @Excel(value = "就学方式", dict = "WAY_OF_STUDYING")
    private Byte wayOfStudying;
    // 健康状况
    private String healthStatus;
    // 身高
    private Double height;
    // 体重
    private Double weight;
    // 是否为留守儿童
    @Excel(value = "是否为留守儿童", dict = "BOOLEAN")
    private Byte isLBC;
    // 是否外来务工人员子女
    @Excel(value = "是否外来务工人员子女", dict = "BOOLEAN")
    private Byte isCOMW;
    // 是否享受“一 补”
    @Excel(value = "是否享受“一 补”", dict = "BOOLEAN")
    private Byte isEOS;
    // 家庭地址
    private String familyAddress;
    // 现住址
    private String currentAddress;
    // 监护人姓名
    private String guardianNames;
    // 监护人电话
    private String guardianPhone;
    // 照片
    private String photo;
    // 院系id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    @JsonIgnore
    private Dept collegeId;
    // 专业id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    @JsonIgnore
    private Dept specialtyId;
    // 年级id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    @JsonIgnore
    private Dept gradeId;
    // 班级id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    @JsonIgnore
    private Dept clazzId;
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