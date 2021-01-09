package com.alan.modules.system.service;

import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.TeachingPlan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/07
 */
public interface TeachingPlanService {

    /**
     * 获取分页列表数据
     *
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<TeachingPlan> getPageList(Example<TeachingPlan> example);

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     */
    TeachingPlan getById(Long id);

    /**
     * 保存数据
     *
     * @param teachingPlan 实体对象
     */
    TeachingPlan save(TeachingPlan teachingPlan);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    /**
     * 查询列表
     *
     * @param example
     * @return
     */
    List<TeachingPlan> getList(Example<TeachingPlan> example);
}