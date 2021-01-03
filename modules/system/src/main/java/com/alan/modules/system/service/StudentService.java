package com.alan.modules.system.service;

import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/30
 */
public interface StudentService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Student> getPageList(Example<Student> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Student getById(Long id);

    /**
     * 保存数据
     * @param student 实体对象
     */
    Student save(Student student);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    /**
     * 根据UserId查询数据
     * @param userId 用户id
     */
    Student getByUserId(Long userId);
}