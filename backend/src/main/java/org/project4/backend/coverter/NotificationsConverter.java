package org.project4.backend.coverter;

import org.project4.backend.dto.NotificationDTO;
import org.project4.backend.entity.NotificationEntity;

public class NotificationsConverter {
    public NotificationDTO mapToDTO(NotificationEntity entity){
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setTaskId(entity.getTaskId());
        dto.setContents(entity.getContents());
        dto.setNotified(entity.getNotified());
        dto.setNotifyAt(entity.getNotifyAt());
        dto.getTask().setId(entity.getTask().getId());
        dto.getTask().setGroupId(entity.getTask().getGroupId());
        dto.getTask().setUserId(entity.getTask().getUserId());
        dto.getTask().setUpdatedAt(entity.getTask().getUpdatedAt());
        dto.getTask().setPriority(entity.getTask().getPriority());
        dto.getTask().setDescription(entity.getTask().getDescription());
        dto.getTask().setTitle(entity.getTask().getTitle());
        dto.getTask().setDueDate(entity.getTask().getDueDate());
        dto.getTask().getUser().setId(entity.getTask().getUserId());
        dto.getTask().getUser().setPasswordHash(null);
        dto.getTask().getUser().setUsername(entity.getTask().getUser().getUsername());
        dto.getTask().getUser().setCreatedAt(entity.getTask().getUser().getCreatedAt());
        dto.getTask().getUser().setEmail(entity.getTask().getUser().getEmail());
        dto.getTask().getUser().setFullName(entity.getTask().getUser().getFullName());
        dto.getTask().getUser().getRoleid().setId(entity.getTask().getUser().getRoleid().getId());
        dto.getTask().getUser().getRoleid().setName(entity.getTask().getUser().getRoleid().getName());
        dto.getTask().getUser().setProfilePicture(entity.getTask().getUser().getProfilePicture());
        dto.getTask().getGroup().setId(entity.getTask().getGroup().getId());
        dto.getTask().getGroup().setName(entity.getTask().getGroup().getName());
        dto.getTask().getGroup().setDescription(entity.getTask().getGroup().getDescription());
        dto.getTask().getGroup().setCreatedAt(entity.getTask().getGroup().getCreatedAt());
        dto.getTask().getGroup().setCreatedBy(entity.getTask().getGroup().getCreatedBy());
        dto.getTask().getGroup().setId(entity.getTask().getGroup().getId());

        dto.getTask().setId(entity.getTask().getId());

        return dto;
    }
}
