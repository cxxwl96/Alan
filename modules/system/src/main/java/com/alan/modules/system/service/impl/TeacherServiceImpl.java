package com.alan.modules.system.service.impl;

import com.alan.common.data.PageSort;
import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Student;
import com.alan.modules.system.domain.Teacher;
import com.alan.modules.system.repository.TeacherRepository;
import com.alan.modules.system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/05
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Teacher getById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Teacher> getPageList(Example<Teacher> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return teacherRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param teacher 实体对象
     */
    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return teacherRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    /**
     * 根据UserId查询数据
     *
     * @param userId 用户id
     */
    @Override
    public Teacher getByUserId(Long userId) {
        return teacherRepository.getByUserId(userId);
    }

    /**
     * 根据NO查找
     *
     * @param no
     * @return
     */
    @Override
    public Teacher getByNo(String no) {
        return teacherRepository.getByNo(no);
    }
}