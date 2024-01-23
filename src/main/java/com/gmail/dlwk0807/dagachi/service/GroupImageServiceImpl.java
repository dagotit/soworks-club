package com.gmail.dlwk0807.dagachi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

//@Service
@RequiredArgsConstructor
@Slf4j
@Deprecated
public class GroupImageServiceImpl implements GroupImageService{

    @Override
    public String uploadGroupImage(MultipartFile file) {
        return null;
    }

    @Override
    public String findImage(Long groupId) throws Exception {
        return null;
    }

    @Override
    public String deleteGroupImage(String objectName) {
        return null;
    }
}
