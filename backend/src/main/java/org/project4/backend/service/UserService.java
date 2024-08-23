package org.project4.backend.service;

import org.project4.backend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void  register (UserDTO dto);
    UserDTO info(String username);
    UserDTO login (String username, String password);
    void updateInfo(UserDTO userDTO);
    void changePassword(Long id, String oldPassword, String newPassword, String confirmPassword);
    UserDTO seachUser(String username,String email);
    void forgotpassword(Long id, String newPassword, String confirmPassword);
   UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
