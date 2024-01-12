package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.image.ProfileImageResponseDTO;
import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import static com.gmail.dlwk0807.dagotit.util.FileUtil.getImage;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupImageService {


    private final GroupRepository groupRepository;

    @Value("${file.groupImagePath}")
    private String uploadFolder;

    public String uploadGroupImage(GroupRequestDTO requestDto) {

        if (StringUtils.isBlank(requestDto.getGroupImgName())) {
            return "anonymous.png";
        }
        String imageFileName = UUID.randomUUID() + "_" + requestDto.getGroupImgName();

        try {
            // 이미지 파일의 바이트를 Base64로 디코딩합니다.
            byte[] imageBase64 = Base64.getDecoder().decode(requestDto.getGroupImg());

            // 이미지 파일을 저장합니다.
            Path targetPath = Paths.get(uploadFolder, imageFileName);
            Files.write(targetPath, imageBase64);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageFileName;
    }

    public String findImage(Long groupId) throws Exception {

        Group group = groupRepository.findById(groupId).orElseThrow();
        String groupImgName = group.getGroupImgName();

        String defaultImageUrl = "anonymous.png";

        if (groupImgName == null) {
            return getImage(uploadFolder + defaultImageUrl);
        } else {
            return getImage(uploadFolder + groupImgName);
        }
    }
}
