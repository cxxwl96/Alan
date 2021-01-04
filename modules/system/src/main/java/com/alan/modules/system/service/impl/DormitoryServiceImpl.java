package com.alan.modules.system.service.impl;

import com.alan.common.data.PageSort;
import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Dormitory;
import com.alan.modules.system.repository.DormitoryRepository;
import com.alan.modules.system.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/04
 */
@Service
public class DormitoryServiceImpl implements DormitoryService {

    @Autowired
    private DormitoryRepository dormitoryRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Dormitory getById(Long id) {
        return dormitoryRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Dormitory> getPageList(Example<Dormitory> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return dormitoryRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param dormitory 实体对象
     */
    @Override
    public Dormitory save(Dormitory dormitory) {
        return dormitoryRepository.save(dormitory);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return dormitoryRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}