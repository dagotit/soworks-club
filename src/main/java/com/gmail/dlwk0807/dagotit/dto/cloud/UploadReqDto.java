package com.gmail.dlwk0807.dagotit.dto.cloud;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Deprecated
public class UploadReqDto {

    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}
