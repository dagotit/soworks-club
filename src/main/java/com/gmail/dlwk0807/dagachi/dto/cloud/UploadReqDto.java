package com.gmail.dlwk0807.dagachi.dto.cloud;

import lombok.Data;

@Data
@Deprecated
public class UploadReqDto {

    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}
