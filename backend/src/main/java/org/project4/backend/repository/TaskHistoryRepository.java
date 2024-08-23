package org.project4.backend.repository;

import org.project4.backend.entity.TaskEntity;
import org.project4.backend.entity.TaskHistoryEntity;
import org.project4.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistoryEntity, Long> {
    List<TaskHistoryEntity> findByTaskId(TaskEntity taskId);
    List<TaskHistoryEntity> findByUserId(UserEntity userId);
}
