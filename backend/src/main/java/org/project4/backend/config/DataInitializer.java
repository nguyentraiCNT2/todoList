package org.project4.backend.config;

import jakarta.annotation.PostConstruct;
import org.project4.backend.entity.RoleEntity;
import org.project4.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;
    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            RoleEntity userRole = new RoleEntity();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

    }
}
