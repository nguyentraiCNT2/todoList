package org.project4.backend.Controller;

import org.project4.backend.dto.NotificationDTO;
import org.project4.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getbytask/{id}")
    public ResponseEntity<?> getByTag(@PathVariable Long id){
        try {
            List<NotificationDTO> notificationDTOS = notificationService.getByTask(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notificationDTOS);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
}
