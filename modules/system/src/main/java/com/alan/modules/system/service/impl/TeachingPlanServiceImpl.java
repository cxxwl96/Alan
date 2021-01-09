package com.alan.modules.system.service.impl;

import com.alan.common.data.PageSort;
import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.TeachingPlan;
import com.alan.modules.system.repository.TeachingPlanRepository;
import com.alan.modules.system.service.TeachingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/07
 */
@Service
public class TeachingPlanServiceImpl implements TeachingPlanService {

    @Autowired
    private TeachingPlanRepository teachingPlanRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public TeachingPlan getById(Long id) {
        return teachingPlanRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<TeachingPlan> getPageList(Example<TeachingPlan> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return teachingPlanRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param teachingPlan 实体对象
     */
    @Override
    public TeachingPlan save(TeachingPlan teachingPlan) {
        return teachingPlanRepository.save(teachingPlan);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return teachingPlanRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    /**
     * 查询列表
     *
     * @param example
     * @return
     */
    @Override
    public List<TeachingPlan> getList(Example<TeachingPlan> example) {
        return teachingPlanRepository.findAll(example);
    }
}