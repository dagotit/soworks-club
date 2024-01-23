package com.gmail.dlwk0807.dagotit.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageCloudService {

    private final Storage storage;

    @Value("${file.bucket}")
    private String bucketName;

    @Value("${file.profileImagePath}")
    private String bucketPath;

    public String uploadProfileImage(MultipartFile file) {

        String fileName = bucketPath + UUID.randomUUID() + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            storage.createFrom(blobInfo, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "https://storage.googleapis.com/" + bucketName + "/"+ fileName;
    }

}
