package com.gmail.dlwk0807.dagotit.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageUploadDTO {

    private MultipartFile file;
    private Long memberId;
}
