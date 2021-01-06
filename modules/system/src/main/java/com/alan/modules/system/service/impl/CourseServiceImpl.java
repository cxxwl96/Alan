package com.alan.modules.system.service.impl;

import com.alan.common.data.PageSort;
import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Course;
import com.alan.modules.system.repository.CourseRepository;
import com.alan.modules.system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/06
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Course getById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Course> getPageList(Example<Course> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return courseRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param course 实体对象
     */
    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return courseRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}