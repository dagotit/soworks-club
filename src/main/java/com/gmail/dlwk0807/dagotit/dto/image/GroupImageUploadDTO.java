package com.gmail.dlwk0807.dagotit.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GroupImageUploadDTO {

    private MultipartFile file;
    private Long groupId;
}
