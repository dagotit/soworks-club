package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//@Service
@RequiredArgsConstructor
@Slf4j
@Deprecated
public class GroupImageServiceImpl implements GroupImageService{


    private final GroupRepository groupRepository;

    @Value("${file.groupImagePath}")
    private String uploadFolder;

    @Override
    public String uploadGroupImage(GroupRequestDTO requestDto, MultipartFile file) {
        return null;
    }

    public String uploadGroupImage(GroupRequestDTO requestDto) {

//        if (StringUtils.isBlank(requestDto.getGroupImgName())) {
//            return "group_default.png";
//        }
//        String imageFileName = UUID.randomUUID() + "_" + requestDto.getGroupImgName();
//
//        try {
//            // 이미지 파일의 바이트를 Base64로 디코딩합니다.
//            byte[] imageBase64 = Base64.getDecoder().decode(requestDto.getGroupImg());
//
//            // 이미지 파일을 저장합니다.
//            Path targetPath = Paths.get(uploadFolder, imageFileName);
//            Files.write(targetPath, imageBase64);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return imageFileName;
        return "";
    }

    public String findImage(Long groupId) throws Exception {

//        Group group = groupRepository.findById(groupId).orElseThrow();
//        String groupImgName = group.getGroupImgName();
//
//        String defaultImageUrl = "anonymous.png";
//
//        if (groupImgName == null) {
//            return getImage(uploadFolder + defaultImageUrl);
//        } else {
//            return getImage(uploadFolder + groupImgName);
//        }
        return null;
    }
}
