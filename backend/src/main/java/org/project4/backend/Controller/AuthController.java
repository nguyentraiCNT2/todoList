package org.project4.backend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project4.backend.context.RequestContext;
import org.project4.backend.dto.RoleDTO;
import org.project4.backend.dto.UserDTO;
import org.project4.backend.securityConfig.CustomUserDetailsService;
import org.project4.backend.securityConfig.JwtTokenUtil;
import org.project4.backend.service.RedisService;
import org.project4.backend.service.TokenService;
import org.project4.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    @Lazy
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/api/auth/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        // Xử lý kiểm tra token
        // Trả về OK nếu token hợp lệ, hoặc UNAUTHORIZED nếu không hợp lệ
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/auth/check-permissions")
    public ResponseEntity<?> checkPermissions(@RequestHeader("Authorization") String token) {
        // Xử lý kiểm tra quyền của người dùng
        // Trả về OK nếu người dùng có quyền, hoặc FORBIDDEN nếu không có quyền
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO user) {
        try {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(2L);
            user.setRoleid(roleDTO);
            userService.register(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            boolean isMatch = BCrypt.checkpw(password, userDetails.getPassword());
            if (!isMatch) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Mật khẩu không chính xác"));
            }
            RequestContext context = RequestContext.get();
            String token = jwtUtil.generateToken(userDetails);
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .maxAge(Duration.ofSeconds(3600))
                    .sameSite("Strict")
                    .secure(true)
                    .path("/")
                    .build();
            // Tạo header
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            if (context != null) {
                response.put("requestId", context.getRequestId());
                response.put("userId", context.getUserId());
                response.put("timestamp", context.getTimestamp());
//                redisService.save(context.getRequestId(), token);
            }
            return ResponseEntity.ok().headers(headers).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Có lỗi không mong muốn: " + e.getMessage()));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed: bạn chưa đăng nhập!");
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                String  jwtToken = requestTokenHeader.substring(7);
                redisService.save(authentication.getName(), jwtToken);
            }
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }
    }
    @GetMapping("/searchuser")
    public ResponseEntity<?> searchuser(@RequestParam String username, @RequestParam String email) {
        try {
            UserDTO userDTO = userService.seachUser(username, email);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }
    }
    @PostMapping("/forgotpassword")
    public ResponseEntity<?> searchuser(@RequestParam Long id, @RequestParam String newPassword
                    , @RequestParam String confirmPassword) {
        try {
           userService.forgotpassword(id, newPassword,confirmPassword);
            return ResponseEntity.ok("Thay đổi mật khẩu thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }

    }
}
