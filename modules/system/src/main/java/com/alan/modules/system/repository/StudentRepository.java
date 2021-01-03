package com.alan.modules.system.repository;

import com.alan.modules.system.domain.Student;
import com.alan.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/30
 */
public interface StudentRepository extends BaseRepository<Student, Long> {
    @Query(value = "SELECT * FROM sims_student WHERE user_id = ?", nativeQuery = true)
    Student getByUserId(Long userId);
}