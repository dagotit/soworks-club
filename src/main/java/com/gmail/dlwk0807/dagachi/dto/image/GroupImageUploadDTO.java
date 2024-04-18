package com.gmail.dlwk0807.dagachi.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GroupImageUploadDTO {

    @Schema(description = "파일첨부", nullable = false, example = "")
    private MultipartFile file;
    @Schema(description = "모임관리번호", nullable = false, example = "1")
    private Long groupId;
}
