package com.alan.modules.system.service;

import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Score;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/06
 */
public interface ScoreService {

    /**
     * 获取分页列表数据
     *
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Score> getPageList(Example<Score> example);

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     */
    Score getById(Long id);

    /**
     * 保存数据
     *
     * @param score 实体对象
     */
    Score save(Score score);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    /**
     * 获取数据列表
     *
     * @param example
     * @return
     */
    List<Score> getList(Example<Score> example);
}