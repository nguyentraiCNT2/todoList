package org.project4.backend.service;

import org.project4.backend.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getByTask(Long id);
}
