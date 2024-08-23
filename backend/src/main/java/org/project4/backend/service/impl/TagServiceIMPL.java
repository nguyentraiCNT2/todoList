package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.TagDTO;
import org.project4.backend.entity.TagEntity;
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
    private ModelMapper modelMapper;
    @Override
    public List<TagDTO> getAll() {
        List<TagDTO> resutf = new ArrayList<>();
        List<TagEntity> tagEntities = tagRepository.findAll();
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
                TagEntity tag = modelMapper.map(tagDTO, TagEntity.class);
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
            modelMapper.map(tagDTO, tag);
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
