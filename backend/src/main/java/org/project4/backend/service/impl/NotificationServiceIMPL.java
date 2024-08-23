package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.NotificationDTO;
import org.project4.backend.entity.NotificationEntity;
import org.project4.backend.entity.TaskEntity;
import org.project4.backend.repository.NotificationsRepository;
import org.project4.backend.repository.TaskRepository;
import org.project4.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NotificationServiceIMPL implements NotificationService {
    @Autowired
    private NotificationsRepository notificationsRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<NotificationDTO> getByTask(Long id) {
        List<NotificationDTO> list = new ArrayList<>();
        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy công việc nào có id là: "+id));
        List<NotificationEntity> notificationEntities= notificationsRepository.findByTask(task);
        for (NotificationEntity item: notificationEntities){
            NotificationDTO dto = modelMapper.map(item, NotificationDTO.class);
            list.add(dto);
        }
        return list ;
    }
}
