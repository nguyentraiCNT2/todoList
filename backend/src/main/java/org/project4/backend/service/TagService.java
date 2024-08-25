package org.project4.backend.service;

import org.project4.backend.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAll(Long groupid);
    void createTag(TagDTO tagDTO);
    void updateTag(TagDTO tagDTO);
    void  deleteTag(Long id);
}
