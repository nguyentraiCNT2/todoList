package org.project4.backend.service;

import org.project4.backend.dto.TaskDTO;

import java.util.List;

public interface TaskService {
       List<TaskDTO> getTaskBytag(Long tagid);
       List<TaskDTO> getByname(String name);
       TaskDTO getById(Long id);
       void createTask(TaskDTO taskDTO, Long tagid );
       void updateTask(TaskDTO taskDTO, Long tagid);
       void deleteTask(Long id);
}
