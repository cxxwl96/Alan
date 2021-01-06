package com.alan.modules.system.repository;

import com.alan.modules.system.domain.Dormitory;
import com.alan.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/04
 */
public interface DormitoryRepository extends BaseRepository<Dormitory, Long> {
    @Query(value = "SELECT * FROM sims_dormitory WHERE students like CONCAT('%',:students,'%')", nativeQuery = true)
    List<Dormitory> findAllByStudents(String students);
}