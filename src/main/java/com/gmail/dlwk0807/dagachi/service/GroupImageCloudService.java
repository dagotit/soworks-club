package com.gmail.dlwk0807.dagachi.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupImageCloudService implements GroupImageService{

    private final Storage storage;

    @Value("${file.bucket}")
    private String bucketName;

    @Value("${file.groupImagePath}")
    private String bucketPath;

    @Override
    public String uploadGroupImage(MultipartFile file) {

        String fileName = bucketPath + UUID.randomUUID() + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            Blob from = storage.createFrom(blobInfo, file.getInputStream());
            log.info("Blob from : {}", from);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "https://storage.googleapis.com/" + bucketName + "/"+ fileName;
    }

    @Override
    public String findImage(Long groupId) throws Exception {
        return null;
    }

    public String deleteGroupImage(String objectName) {
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
            Blob blob = storage.get(bucketName, filename);
            if (blob == null) {
                System.out.println("The object " + filename + " wasn't found in " + bucketName);
                return null;
            }

            Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());

            storage.delete(bucketName, filename, precondition);
        }


        return "Object " + filename + " was deleted from " + bucketName;
    }
}
