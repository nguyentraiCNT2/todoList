package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.config.MapperConfig;
import org.project4.backend.dto.TaskHistoryDTO;
import org.project4.backend.entity.TaskHistoryEntity;
import org.project4.backend.entity.UserEntity;
import org.project4.backend.repository.TaskHistoryRepository;
import org.project4.backend.repository.UserRepository;
import org.project4.backend.service.TaskHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskHistoryServiceIMPL implements TaskHistoryService {
    @Autowired
    private TaskHistoryRepository taskHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final MapperConfig notificationMapper = MapperConfig.INSTANCE;
    @Override
    public List<TaskHistoryDTO> getByUserid(Long userid) {
        List<TaskHistoryDTO> resuft = new ArrayList<>();
        UserEntity user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có id là: "+userid));
        List<TaskHistoryEntity > taskHistoryEntities = taskHistoryRepository.findByUser(user);
        for (TaskHistoryEntity item: taskHistoryEntities){
            resuft.add(notificationMapper.toTaskHistoryDTO(item));
        }
        return resuft;
    }
}
