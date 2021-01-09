package com.alan.modules.system.domain;

import com.alan.component.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.StatusUtil;
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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/02
 */
@Data
@Entity
@Table(name = "sys_dept")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Dept implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门名称
     */
    private String title;

    /**
     * 父级编号
     */
    private Long pid;

    /**
     * 所有父级编号
     */
    private String pids;

    /**
     * 层级
     */
    @Excel(value = "层级", dict = "DEPT_LEVEL")
    private Byte level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createDate;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private Date updateDate;

    /**
     * 创建者
     */
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "create_by")
    @JsonIgnore
    private User createBy;

    /**
     * 更新者
     */
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "update_by")
    @JsonIgnore
    private User updateBy;

    /**
     * 数据状态
     */
    private Byte status = StatusEnum.OK.getCode();
}
