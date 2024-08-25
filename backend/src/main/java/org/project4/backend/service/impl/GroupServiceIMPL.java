package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.GroupDTO;
import org.project4.backend.entity.GroupEntity;
import org.project4.backend.entity.GroupMemberEntity;
import org.project4.backend.entity.UserEntity;
import org.project4.backend.repository.GroupMemberRepository;
import org.project4.backend.repository.GroupRepository;
import org.project4.backend.repository.UserRepository;
import org.project4.backend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceIMPL implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<GroupDTO> getByMember(Long memberid) {
        List<GroupDTO> resutf = new ArrayList<>();
        UserEntity user = userRepository.findById(memberid).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có id là: "+ memberid));
        List<GroupMemberEntity> groupMemberEntities = groupMemberRepository.findByUser(user);
        if (groupMemberEntities.size() ==0)
            throw  new RuntimeException("Bạn không có nhóm nào vui lòng tạo hoặc tham giá một nhóm.");
        for (GroupMemberEntity item :groupMemberEntities ){
            GroupDTO groupDTO = modelMapper.map(item.getGroup(),GroupDTO.class );
            resutf.add(groupDTO);
        }
        return resutf;
    }

    @Override
    public void createGroup(GroupDTO groupDTO) {
        try {
            LocalDateTime now = LocalDateTime.now();
            if (groupDTO.getName() == null || groupDTO.getName() == "")
                throw new RuntimeException("Bạn chưa nhập tên của nhóm");
            if (groupDTO.getDescription() == null || groupDTO.getDescription() =="")
                throw new RuntimeException("Bạn chưa nhập mô tải của nhóm");
            UserEntity user = userRepository.findById(groupDTO.getCreatedBy())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có id là: "+groupDTO.getCreatedBy()));
            groupDTO.setCreatedAt(now);
            GroupEntity group = modelMapper.map(groupDTO, GroupEntity.class);
            group.setCreatedByUser(user);
            GroupEntity group_save = groupRepository.save(group);
            GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
            groupMemberEntity.setGroup(group_save);
            groupMemberEntity.setUser(user);
            groupMemberEntity.setRole("ADMIN");
            groupMemberRepository.save(groupMemberEntity);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi sảy ra khi thêm mới: "+e.getMessage());
        }
    }

    @Override
    public void addMember(Long groupid, Long userid) {
        GroupEntity group = groupRepository.findById(groupid).orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm nào có id là: "+ groupid));
        UserEntity user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có id là: "+userid));
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setGroup(group);
        groupMemberEntity.setUser(user);
        groupMemberEntity.setRole("MEMBER");
        groupMemberRepository.save(groupMemberEntity);
    }

    @Override
    public void quitMember(Long groupId, Long userId, Long deleteUserId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm nào có id là: " + groupId));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("không tìm thấy người dùng nào có id là: "+userId));
        GroupMemberEntity requestingMember = groupMemberRepository.findByGroupAndUser(group, user);
        if (!requestingMember.getRole().equals("ADMIN")) {
            throw new RuntimeException("Chỉ có trưởng nhóm mới có thể xóa người dùng");
        }
        UserEntity deleteuser = userRepository.findById(deleteUserId).orElseThrow(() -> new RuntimeException("không tìm thấy người dùng nào có id là: "+deleteUserId));
        GroupMemberEntity deleteUserMember = groupMemberRepository.findByGroupAndUser(group, deleteuser);
        groupMemberRepository.delete(deleteUserMember);
    }

    @Override
    public void updateGroup(GroupDTO groupDTO , Long userid) {
        try {
            if (groupDTO.getName() == null || groupDTO.getName() == "")
                throw new RuntimeException("Bạn chưa nhập tên của nhóm");
            if (groupDTO.getDescription() == null || groupDTO.getDescription() =="")
                throw new RuntimeException("Bạn chưa nhập mô tải của nhóm");
            GroupEntity entity = groupRepository.findById(groupDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm nào có id là: "+ groupDTO.getId()));
            List<GroupMemberEntity> groupMemberEntities= groupMemberRepository.findByGroup(entity);
            for ( GroupMemberEntity item: groupMemberEntities) {
                if (item.getUser().getId() != userid)
                    throw new RuntimeException("Bạn không ở trong nhóm này ");
                if (!item.getRole().equals("ADMIN"))
                    throw  new RuntimeException("Chỉ có trưởng nhóm mới có thể chỉnh sửa nhóm");
            }
            entity.setName(groupDTO.getName());
            entity.setDescription(groupDTO.getDescription());
          groupRepository.save(entity);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi sảy ra khi thêm mới: "+e.getMessage());
        }
    }

    @Override
    public void delateGroup(Long id, Long userid) {
        GroupEntity group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm nào có id là: "+ id));
        List<GroupMemberEntity> groupMemberEntities= groupMemberRepository.findByGroup(group);
        if (groupMemberEntities.size() ==0)
            throw  new RuntimeException("Bạn không Bạn không có nhóm này ");
        for ( GroupMemberEntity item: groupMemberEntities) {
            if (item.getUser().getId() != userid)
                throw  new RuntimeException("Bạn không ở trong nhóm này ");
            if (!item.getRole().equals("ADMIN"))
                throw  new RuntimeException("Chỉ có trưởng nhóm mới có thể xóa ");
            groupMemberRepository.delete(item);

        }
        groupRepository.delete(group);
    }
}
