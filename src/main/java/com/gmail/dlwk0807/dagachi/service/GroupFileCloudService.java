package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.entity.GroupFile;
import com.gmail.dlwk0807.dagachi.repository.GroupFileRepository;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.gmail.dlwk0807.dagachi.util.GCSCloudUtils.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupFileCloudService {

    private final Storage storage;
    private final GroupFileRepository groupFileRepository;

    @Value("${file.bucket}")
    private String bucketName;

    @Value("${file.groupFilePath}")
    private String bucketPath;

    public String uploadGroupFile(MultipartFile file, Long groupId) {

        List<GroupFile> groupFileList = groupFileRepository.findAllByGroupId(groupId);
        //파일 이름 같으면 삭제
        for (GroupFile groupFile : groupFileList) {
            if (groupFile.getOriginalName().equals(file.getOriginalFilename())) {
                //삭제로직
                String deleteFileName = bucketPath + groupId + File.separator + groupFile.getSaveName();
                deleteFile(storage, bucketName, deleteFileName);
                groupFileRepository.delete(groupFile);
            }
        }
        String saveName = UUID.randomUUID() + file.getOriginalFilename();
        String fileName = bucketPath + groupId + File.separator + saveName;
        uploadFile(storage, bucketName, fileName, file);

        return saveName;
    }

    public String deleteGroupImageFolder(Long groupId) {
        String folderPath = bucketPath + groupId;
        deleteFolder(storage, bucketName, folderPath);

        return "Folder " + folderPath + " was deleted from " + bucketName;
    }
}
