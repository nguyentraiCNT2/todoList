package org.project4.backend.Controller;

import org.project4.backend.dto.GroupDTO;
import org.project4.backend.dto.TaskDTO;
import org.project4.backend.dto.UserDTO;
import org.project4.backend.service.GroupService;
import org.project4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @GetMapping("/getbymember")
    public ResponseEntity<?> getbymember(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            List<GroupDTO> groupDTOS = groupService.getByMember(userDTO.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(groupDTOS);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/creategroup")
    public ResponseEntity<?> creategroup(@RequestBody GroupDTO groupDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            groupDTO.setCreatedBy(userDTO.getId());
             groupService.createGroup(groupDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Thêm mới thành công");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/addmember/{id}")
    public ResponseEntity<?> addmember(@PathVariable Long id,@RequestParam("userid") Long userid){
        try {
            groupService.addMember(id, userid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Thêm mới thành công");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/quitmenber/{id}")
    public ResponseEntity<?> quitmenber(@PathVariable Long id,@RequestParam("userid") Long userid){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            groupService.quitMember(id, userDTO.getId(),userid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Thêm mới thành công");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @PutMapping("/updategroup/{id}")
    public ResponseEntity<?> creategroup(@PathVariable Long id,@RequestBody GroupDTO groupDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            groupDTO.setId(id);
            groupService.updateGroup(groupDTO,userDTO.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cập nhật nhóm thành công");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @DeleteMapping("/deletegroup/{id}")
    public ResponseEntity<?> deletegroup(@PathVariable Long id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            groupService.delateGroup(id,userDTO.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("xóa nhóm thành công");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
}
