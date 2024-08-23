package org.project4.backend.repository;

import org.project4.backend.entity.NotificationEntity;
import org.project4.backend.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationsRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByTask(TaskEntity task);
}
