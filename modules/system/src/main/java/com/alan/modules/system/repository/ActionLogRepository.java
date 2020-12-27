package com.alan.modules.system.repository;

import com.alan.modules.system.domain.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2020/10/19
 */
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    /**
     * 根据模型和数据ID查询日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     * @return 日志列表
     */
    public List<ActionLog> findByModelAndRecordId(String model, Long recordId);
}
