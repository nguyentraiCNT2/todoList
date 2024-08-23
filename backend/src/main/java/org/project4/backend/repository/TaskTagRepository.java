package org.project4.backend.repository;

import org.project4.backend.entity.TagEntity;
import org.project4.backend.entity.TaskEntity;
import org.project4.backend.entity.TaskTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskTagRepository extends JpaRepository<TaskTagEntity, Long> {
    List<TaskTagEntity> findByTask(TaskEntity task);
    List<TaskTagEntity> findByTag(TagEntity tag);
}
