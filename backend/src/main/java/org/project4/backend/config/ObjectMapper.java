package org.project4.backend.config;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project4.backend.dto.GroupDTO;
import org.project4.backend.dto.NotificationDTO;
import org.project4.backend.dto.TaskDTO;
import org.project4.backend.dto.TaskHistoryDTO;
import org.project4.backend.entity.GroupEntity;
import org.project4.backend.entity.NotificationEntity;
import org.project4.backend.entity.TaskEntity;
import org.project4.backend.entity.TaskHistoryEntity;

@Mapper
public interface ObjectMapper {
    ObjectMapper INSTANCE = Mappers.getMapper(ObjectMapper.class);

    NotificationDTO toNotificationDTO(NotificationEntity notificationEntity);
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
    TaskHistoryDTO toTaskHistoryDTO(TaskHistoryEntity taskHistoryEntity);
    TaskHistoryEntity toTaskHistoryEntity(TaskHistoryDTO taskHistoryDTO);
    TaskDTO toTaskDTO(TaskEntity entity);
    TaskEntity toTaskEntity(TaskDTO dto);



}
