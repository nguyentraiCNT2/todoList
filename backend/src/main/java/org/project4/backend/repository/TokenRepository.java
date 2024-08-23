package org.project4.backend.repository;

import org.project4.backend.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findByResetToken(String token);
    Boolean existsByResetToken(String token);
}
