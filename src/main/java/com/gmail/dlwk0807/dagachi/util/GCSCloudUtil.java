package com.gmail.dlwk0807.dagachi.util;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GCSCloudUtil {

    public static String uploadFile(Storage storage, String bucketName, String fileName, MultipartFile file) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            Blob from = storage.createFrom(blobInfo, file.getInputStream());
            log.info("Blob from : {}", from);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "ok";
    }

    public static String deleteFile(Storage storage, String bucketName, String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        if (blob == null) {
            log.info("The object " + fileName + " wasn't found in " + bucketName);
            throw new CustomRespBodyException("저장된 파일이 존재하지 않습니다.");
        }
        Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());
        storage.delete(bucketName, fileName, precondition);

        return "ok";
    }

    public static String deleteFolder(Storage storage, String bucketName, String folderPath) {
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(folderPath));

        for (Blob blob : blobs.iterateAll()) {
            Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());
            storage.delete(bucketName, blob.getName(), precondition);
        }

        return "ok";
    }

    public static String listFolder(Storage storage, String bucketName, String folderPath) {
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(folderPath));

        for (Blob blob : blobs.iterateAll()) {
            Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());
        }

        return "ok";
    }

}