package com.gmail.dlwk0807.dagotit.service;

import org.springframework.web.multipart.MultipartFile;

public interface GroupImageService {
    String uploadGroupImage(MultipartFile file);
    String findImage(Long groupId) throws Exception;
    String deleteGroupImage(String objectName);
}
