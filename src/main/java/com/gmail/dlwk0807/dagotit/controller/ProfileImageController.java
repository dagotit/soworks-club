package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.image.ProfileImageUploadDTO;
import com.gmail.dlwk0807.dagotit.service.ProfileImageService;
import com.gmail.dlwk0807.dagotit.util.SecurityUtil;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile-image")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO upload(MultipartFile file, Long memberId, @AuthenticationPrincipal User user) {
        ProfileImageUploadDTO profileImageUploadDTO = new ProfileImageUploadDTO();
        profileImageUploadDTO.setFile(file);
        profileImageUploadDTO.setMemberId(memberId);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(profileImageService.upload(profileImageUploadDTO, user))
                .respCode(OK_RESP_CODE)
                .build();
    }
}
