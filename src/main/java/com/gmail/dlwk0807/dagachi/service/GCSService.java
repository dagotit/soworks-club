package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.dto.cloud.UploadReqDto;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GCSService {

    private final Storage storage;

    @SuppressWarnings("deprecation")
    public BlobInfo uploadFileToGCS(UploadReqDto uploadReqDto) throws IOException {

        try(FileInputStream fileInputStream = new FileInputStream(getClass().getResource("/tmpImage/test1.png").getFile())) {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName())
                            .build(),
                    fileInputStream);
            return blobInfo;
        } catch (Exception e) {
            log.error("GCS 파일 업로드 실패");
        }
        return null;
    }

}
