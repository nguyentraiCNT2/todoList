package org.project4.backend.Controller;

import org.project4.backend.dto.TagDTO;
import org.project4.backend.dto.TaskDTO;
import org.project4.backend.dto.UserDTO;
import org.project4.backend.service.TaskService;
import org.project4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping("/getbytag")
    public ResponseEntity<?> getByTag(@RequestParam Long tagid, @RequestParam Long groupid){
        try {
            List<TaskDTO> taskDTOList = taskService.getTaskBytag(tagid,groupid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskDTOList);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/createtask/{id}")
    public ResponseEntity<?> createTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            taskDTO.setUserId(userDTO.getId());
              taskService.createTask(taskDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Thêm mới công việc thành công.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @PutMapping("/updatetask/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        try {
            taskService.updateTask(taskDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cập nhật công việc thành công.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletetask/{id}")
    public ResponseEntity<?> deleteTaks(@PathVariable Long id){
        try {
            taskService.deleteTask( id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Đã xóa công việc thành công.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("failed: " + e.getMessage());
        }
    }


}
