package org.project4.backend.repository;

import org.project4.backend.entity.GroupEntity;
import org.project4.backend.entity.GroupMemberEntity;
import org.project4.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
    List<GroupMemberEntity> findByUser(UserEntity user);
    List<GroupMemberEntity> findByGroup(GroupEntity group);
    GroupMemberEntity findByGroupAndUser( GroupEntity group , UserEntity user);
}
