package org.project4.backend.dto;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String passwordHash;

    private String fullName;

    private String profilePicture;

    private LocalDateTime createdAt;

    private RoleDTO roleid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public RoleDTO getRoleid() {
        return roleid;
    }

    public void setRoleid(RoleDTO roleid) {
        this.roleid = roleid;
    }
}
