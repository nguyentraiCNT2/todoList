package org.project4.backend.Controller;

import org.project4.backend.dto.TagDTO;
import org.project4.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        try {
            List<TagDTO> tagDTOList = tagService.getAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tagDTOList);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> CreateTag(@RequestBody TagDTO tagDTO){
        try {
            tagService.createTag(tagDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Thêm mới thành công.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTag(@PathVariable Long id,@RequestBody TagDTO tagDTO){
        try {
            tagDTO.setId(id);
            tagService.updateTag(tagDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cập nhật thành công.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id){
        try {
            tagService.deleteTag(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cập nhật thành công.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: " + e.getMessage());
        }
    }

}
