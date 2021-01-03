package com.alan.modules.system.service.impl;

import com.alan.common.data.PageSort;
import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Student;
import com.alan.modules.system.repository.StudentRepository;
import com.alan.modules.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/30
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Student getById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Student> getPageList(Example<Student> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return studentRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param student 实体对象
     */
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return studentRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    /**
     * 根据UserId查询数据
     *
     * @param userId 用户id
     */
    @Override
    public Student getByUserId(Long userId) {
        return studentRepository.getByUserId(userId);
    }
}