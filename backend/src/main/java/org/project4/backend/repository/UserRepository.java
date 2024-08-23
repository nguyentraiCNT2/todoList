package org.project4.backend.repository;

import org.project4.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUsername(String username);
    @Query("select  u from UserEntity u where u.email = :email  ")
    List<UserEntity> findByEmail(@Param("email") String email);
    Boolean existsByEmail(String Email);
    Boolean existsByUsername(String username);
    Boolean existsByUsernameAndEmail(String username,String email);

 }
