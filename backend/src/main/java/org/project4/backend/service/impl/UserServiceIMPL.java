package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.UserDTO;
import org.project4.backend.entity.RoleEntity;
import org.project4.backend.entity.UserEntity;
import org.project4.backend.repository.RoleRepository;
import org.project4.backend.repository.UserRepository;
import org.project4.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepository userRepository;
@Autowired
private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;

    public void register(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        RoleEntity role = roleRepository.findById(userEntity.getRoleid().getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy quyền hạn có id này"));
      Boolean checkemail = userRepository.existsByEmail(userDTO.getEmail());
        Boolean checkusername = userRepository.existsByUsername(userDTO.getUsername());
        if (checkemail)
            throw new RuntimeException("Email này đã tồn tại.");
        if (checkusername)
            throw new RuntimeException("tài khoản này đã tồn tại.");
        if (userEntity.getPasswordHash().length() < 6)
            throw new RuntimeException("mật khẩu tối thiểu 6 ký tự");
        if (userEntity.getPasswordHash().length() > 25)
            throw new RuntimeException("mật khẩu tối đa 25 ký tự");

        LocalDateTime now = LocalDateTime.now();
        String hashPassword =  BCrypt.hashpw(userEntity.getPasswordHash(), BCrypt.gensalt());
        userEntity.setPasswordHash(hashPassword);
        userEntity.setCreatedAt(now);
        userEntity.setRoleid(role);
        userRepository.save(userEntity);
    }

    @Override
    public UserDTO info(String username) {
        List<UserEntity>userEntities = userRepository.findByUsername(username);
        UserDTO dto = modelMapper.map(userEntities.get(0), UserDTO.class);
        return dto;
    }

    public UserDTO login(String username, String password) {
       List<UserEntity>  userEntity = userRepository.findByUsername(username);
        if (userEntity != null && userEntity.get(0).getPasswordHash().equals(password)) {
            UserDTO userDTO = modelMapper.map(userEntity,UserDTO.class);
            return userDTO;
        }
        return null;
    }

    @Override
    public void updateInfo(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail() =="")
            throw new RuntimeException("Bạn chưa nhập email");
        if (userDTO.getFullName() == null || userDTO.getFullName() =="")
            throw new RuntimeException("Bạn chưa nhập họ tên");
        UserEntity user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng có id là: "+userDTO.getId()));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        userRepository.save(user);

    }

    @Override
    public void changePassword(Long id,String oldPassword, String newPassword, String confirmPassword) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng có id là: "+id));
        if (newPassword.length() < 6)
            throw new RuntimeException("mật khẩu tối thiểu 6 ký tự");
        if (confirmPassword.length() > 25)
            throw new RuntimeException("mật khẩu tối đa 25 ký tự");
        if (!confirmPassword.equals(newPassword) )
            throw new RuntimeException("mật khẩu mới không trùng khớp");
        if (newPassword.equals(oldPassword) )
            throw new RuntimeException("mật khẩu mới không thể giống mật khẩu cũ");
        String hashPassword =  BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(hashPassword);
        userRepository.save(user);

    }

    @Override
    public UserDTO seachUser(String username, String email) {
        List<UserEntity> userEntities = userRepository.findByUsername(username);
        UserDTO dto = new UserDTO();
        for (UserEntity item: userEntities ){
            if (item.getEmail().equals(email))
             dto = modelMapper.map(item,UserDTO.class);
                return dto;
        }
        return null;
    }

    @Override
    public void forgotpassword(Long id,String newPassword, String confirmPassword) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng có id là: "+id));
        if (newPassword.length() < 6)
            throw new RuntimeException("mật khẩu tối thiểu 6 ký tự");
        if (confirmPassword.length() > 25)
            throw new RuntimeException("mật khẩu tối đa 25 ký tự");
        if (!confirmPassword.equals(newPassword) )
            throw new RuntimeException("mật khẩu mới không trùng khớp");
        String hashPassword =  BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(hashPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity>  userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.get(0).getUsername())
                .password(userEntity.get(0).getPasswordHash())
                .roles(userEntity.get(0).getRoleid().getName())
                .build();
    }
}
