package com.alan.modules.system.repository;

import com.alan.modules.system.domain.Student;
import com.alan.modules.system.domain.Teacher;
import com.alan.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/05
 */
public interface TeacherRepository extends BaseRepository<Teacher, Long> {
    @Query(value = "SELECT * FROM sims_teacher WHERE user_id = ?", nativeQuery = true)
    Teacher getByUserId(Long userId);

    @Query(value = "SELECT * FROM sims_teacher WHERE no = ?", nativeQuery = true)
    Teacher getByNo(String no);
}