package org.project4.backend.service;

import org.project4.backend.dto.TaskDTO;
import org.project4.backend.dto.TaskHistoryDTO;

import java.util.List;

public interface TaskHistoryService {
    List<TaskHistoryDTO>getByUserid(Long userid );
}
