package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface GroupImageService {
    String uploadGroupImage(GroupRequestDTO requestDto, MultipartFile file);
    String uploadGroupImage(GroupRequestDTO requestDto);
    String findImage(Long groupId) throws Exception;
}
