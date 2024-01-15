package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.cloud.UploadReqDto;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class GCSService {

    private final Storage storage;

    @SuppressWarnings("deprecation")
    public BlobInfo uploadFileToGCS(UploadReqDto uploadReqDto) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(getClass().getResource("/tmpImage/test1.png").getFile());
        BlobInfo blobInfo =storage.create(
                BlobInfo.newBuilder(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName())
                        .build(),
                        fileInputStream);

        return blobInfo;
    }

}
