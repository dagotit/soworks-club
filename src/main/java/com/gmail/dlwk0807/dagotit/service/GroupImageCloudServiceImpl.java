package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Profile("local")
public class GroupImageCloudServiceImpl implements GroupImageService{

    private final Storage storage;

    @Value("${file.bucket}")
    private String bucketName;

    @Value("${file.groupImagePath}")
    private String bucketPath;

    @Override
    public String uploadGroupImage(GroupRequestDTO requestDto, MultipartFile file) {

        String fileName = bucketPath + UUID.randomUUID() + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            Blob from = storage.createFrom(blobInfo, file.getInputStream());
            System.out.println("from = " + from);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "https://storage.googleapis.com/" + bucketName + "/"+ fileName;
    }

    @Override
    public String uploadGroupImage(GroupRequestDTO requestDto) {
        return null;
    }

    @Override
    public String findImage(Long groupId) throws Exception {
        return null;
    }
}
