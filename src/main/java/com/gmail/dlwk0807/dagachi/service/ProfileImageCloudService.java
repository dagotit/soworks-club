package com.gmail.dlwk0807.dagachi.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.gmail.dlwk0807.dagachi.util.GCSCloudUtils.deleteFile;
import static com.gmail.dlwk0807.dagachi.util.GCSCloudUtils.uploadFile;

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
        uploadFile(storage, bucketName, fileName, file);

        return "https://storage.googleapis.com/" + bucketName + "/"+ fileName;
    }

    public String deleteProfileImage(String objectName) {
        //objectName = "https://storage.googleapis.com/dagachi-image-bucket/local/profileImages/fd439c25-ff55-42b6-aa30-542e5efd051fnoname3.png"
        String prefix = bucketName + "/";
        int start = objectName.indexOf(prefix);
        int prefixLength = prefix.length();

        //objectName = "local/profileImages/fd439c25-ff55-42b6-aa30-542e5efd051fnoname3.png"
        String filename = objectName.substring(start + prefixLength);
        if (filename.startsWith("default/")) {
            return "default 이미지 삭제 제외";
        }

        if (start != -1) {
            deleteFile(storage, bucketName, filename);
        }

        return "Object " + filename + " was deleted from " + bucketName;
    }
}
