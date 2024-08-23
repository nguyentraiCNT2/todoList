package org.project4.backend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.project4.backend.context.RequestContext;
import org.project4.backend.dto.UserDTO;
import org.project4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    @Autowired
    private UserService userService;
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getMe(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            userDTO.setPasswordHash(null);
            RequestContext context = RequestContext.get();
            Map<String, Object> response = new HashMap<>();
            response.put("userDTO", userDTO);
            if (context != null) {
                response.put("requestId", context.getRequestId());
                response.put("userId", context.getUserId());
                response.put("timestamp", context.getTimestamp());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Có lỗi không mong muốn: " + e.getMessage()));
        }
    }
    @PostMapping("/updateinfo")
    public ResponseEntity<?> updateinfo(@RequestBody UserDTO userDTO) {
        try {
            userService.updateInfo(userDTO);
            return ResponseEntity.ok("Cập nhật thông tin tài khoản thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }
    }
    @PostMapping("/changepassword")
    public ResponseEntity<?> changepassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = userService.info(authentication.getName());
            userService.changePassword(userDTO.getId(), oldPassword,newPassword,confirmPassword);
            return ResponseEntity.ok("Cập nhật mật khẩu thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }
    }
}
