package org.project4.backend.service;

import org.project4.backend.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAll();
    void createTag(TagDTO tagDTO);
    void updateTag(TagDTO tagDTO);
    void  deleteTag(Long id);
}
