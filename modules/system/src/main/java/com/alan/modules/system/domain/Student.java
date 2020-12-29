package com.alan.modules.system.domain;

import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.StatusUtil;
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
 * @date 2020/12/29
 */
@Data
@Entity
@Table(name="sims_student")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class Student implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    // 系统用户
    private User user;
    // 学号
    private String stuNo;
    // 学籍号
    private String stuNumber;
    // 姓名
    private String names;
    // 曾用名
    private String formerNames;
    // 性别
    private String sex;
    // 出生年月
    private String birthday;
    // 身份证类型
    private String IDType;
    // 证件号
    private String IDNo;
    // 民族
    private String nation;
    // 户籍所在地
    private String domicile;
    // 入学年月
    private String admissionTime;
    // 人学方式
    private String anthropology;
    // 就学方式
    private String wayOfStudying;
    // 健康状况
    private String healthStatus;
    // 身高
    private String height;
    // 体重
    private String weight;
    // 是否为留守儿童
    private String isLBC;
    // 是否外来务工人员子女
    private String isCOMW;
    // 是否享受“一 补”
    private String isEOS;
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
    private String collegeId;
    // 专业id
    private String specialtyId;
    // 年级id
    private String gradeId;
    // 班级id
    private String clazzId;

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