// CustomUserDetailsService.java
package org.project4.backend.securityConfig;
import org.project4.backend.context.RequestContext;
import org.project4.backend.entity.RoleEntity;
import org.project4.backend.entity.UserEntity;
import org.project4.backend.repository.RoleRepository;
import org.project4.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByUsername(username);
        if (userEntities.isEmpty()) {
            throw new UsernameNotFoundException("Không có tài khoản nào có tên: " + username);
        }
        RequestContext context = RequestContext.get();
        context.setUserId(userEntities.get(0).getId());
        RoleEntity roleEntity = roleRepository.findById(userEntities.get(0).getRoleid().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Không có quyền hạn này"));

        users.add(User.withUsername(userEntities.get(0).getUsername())
                .password(userEntities.get(0).getPasswordHash())
                .roles(roleEntity.getName())
                .build());

        return users.get(0);
    }
}