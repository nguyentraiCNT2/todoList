package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.TagDTO;
import org.project4.backend.entity.GroupEntity;
import org.project4.backend.entity.TagEntity;
import org.project4.backend.repository.GroupRepository;
import org.project4.backend.repository.TagRepository;
import org.project4.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceIMPL implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<TagDTO> getAll(Long groupid) {
        List<TagDTO> resutf = new ArrayList<>();
        GroupEntity group = groupRepository.findById(groupid).orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm có id: "+groupid+" này"));
        List<TagEntity> tagEntities = tagRepository.findByGroup(group);
        if (tagEntities.size() ==0)
            throw new RuntimeException("Không có nhãn nào trong nhóm ");
        for (TagEntity item: tagEntities){
            TagDTO dto = modelMapper.map(item, TagDTO.class);
            resutf.add(dto);
        }

        return resutf;
    }

    @Override
    public void createTag(TagDTO tagDTO) {
        try {
                if (tagDTO.getName() == null || tagDTO.getName() == "")
                    throw new RuntimeException("Bạn chưa nhập tên nhãn");
            GroupEntity group = groupRepository.findById(tagDTO.getGroup().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm có id: "+tagDTO.getGroup().getId()+" này"));

            TagEntity tag = modelMapper.map(tagDTO, TagEntity.class);
            tag.setGroup(group);
                tagRepository.save(tag);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi sảy ra khi thêm mới.");
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        try {
            if (tagDTO.getId() == null)
                throw new RuntimeException("Bạn chưa đưa id và0");
            if (tagDTO.getName() == null || tagDTO.getName() == "")
                throw new RuntimeException("Bạn không thể để trống tên nhãn");
            TagEntity tag = tagRepository.findById(tagDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Không có nhãn nào có id là: "+tagDTO.getId()));
            tag.setName(tagDTO.getName());
            tagRepository.save(tag);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi không xác định khi sửa: "+e.getMessage());
        }
    }

    @Override
    public void deleteTag(Long id) {
        try {
            TagEntity entity = tagRepository.findById(id)
                    .orElseThrow(() -> new  RuntimeException("Không tìm thấy nhãn nào có id là:" +id));
                tagRepository.delete(entity);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi sảy ra khi xóa nhãn");
        }
    }
}
