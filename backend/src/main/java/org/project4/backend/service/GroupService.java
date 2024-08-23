package org.project4.backend.service;

import org.project4.backend.dto.GroupDTO;

import java.util.List;

public interface GroupService {
     List<GroupDTO> getByMember(Long memberid);
     void createGroup(GroupDTO groupDTO);
     void addMember(Long groupid, Long userid);
     void quitMember(Long groupid, Long userid, Long deleteUserid);
     void updateGroup(GroupDTO groupDTO , Long userid);
     void delateGroup(Long id, Long userid);
}
