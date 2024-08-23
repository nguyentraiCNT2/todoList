package org.project4.backend.Controller;

import org.project4.backend.dto.TaskDTO;
import org.project4.backend.dto.TaskHistoryDTO;
import org.project4.backend.dto.UserDTO;
import org.project4.backend.service.TaskHistoryService;
import org.project4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TaskHistoryController {
    @Autowired
    private TaskHistoryService taskHistoryService;
    @Autowired
    private UserService userService;

    @GetMapping("/getbytag")
    public ResponseEntity<?> getByTag(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            List<TaskHistoryDTO> taskHistoryDTOS = taskHistoryService.getByUserid(userDTO.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskHistoryDTOS);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }

}
