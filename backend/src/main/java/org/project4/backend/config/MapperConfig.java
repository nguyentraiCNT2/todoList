package org.project4.backend.config;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project4.backend.dto.NotificationDTO;
import org.project4.backend.dto.TaskHistoryDTO;
import org.project4.backend.entity.NotificationEntity;
import org.project4.backend.entity.TaskHistoryEntity;

@Mapper
public interface  MapperConfig {
    MapperConfig INSTANCE = Mappers.getMapper(MapperConfig.class);

    NotificationDTO toNotificationDTO(NotificationEntity notificationEntity);
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
    TaskHistoryDTO toTaskHistoryDTO(TaskHistoryEntity taskHistoryEntity);
    TaskHistoryEntity toTaskHistoryEntity(TaskHistoryDTO taskHistoryDTO);
}
