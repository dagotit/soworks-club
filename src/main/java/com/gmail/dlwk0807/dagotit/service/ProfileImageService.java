package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.dto.image.ProfileImageResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.image.ProfileImageUploadDTO;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.entity.ProfileImage;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.repository.ProfileImageRepository;
import com.gmail.dlwk0807.dagotit.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

import static com.gmail.dlwk0807.dagotit.util.FileUtil.deleteFile;
import static com.gmail.dlwk0807.dagotit.util.FileUtil.getImage;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileImageService {


    private final ProfileImageRepository profileImageRepository;
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;

    @Value("${file.profileImagePath}")
    private String uploadFolder;

    public String upload(ProfileImageUploadDTO profileImageUploadDTO, User user) {

        if (!authUtil.isAdmin()) {
            String currentMemberId = user.getUsername();
            if (!currentMemberId.equals(profileImageUploadDTO.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        Member member = memberRepository.findById(profileImageUploadDTO.getMemberId()).orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        MultipartFile file = profileImageUploadDTO.getFile();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(uploadFolder + imageFileName);

        try {
            file.transferTo(destinationFile);

            ProfileImage image = profileImageRepository.findByMember(member).orElseThrow();
            String oldFileName = image.getUrl();

            image.updateUrl(imageFileName);

            //기본이미지일 경우 업데이트만하고 기존이미지 삭제하지 않는다.
            if (!"anonymous.png".equals(oldFileName)) {
                // 기존 파일 삭제
                deleteFile(uploadFolder + oldFileName);
            }

            profileImageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "업로드 성공";
    }

    public ProfileImageResponseDTO findImage(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        ProfileImage image = profileImageRepository.findByMember(member).orElseThrow();

        String defaultImageUrl = "anonymous.png";

        if (image == null) {
            return ProfileImageResponseDTO.builder()
                    .profileImg(getImage(uploadFolder + defaultImageUrl))
                    .build();
        } else {
            return ProfileImageResponseDTO.builder()
                    .profileImg(getImage(uploadFolder + image.getUrl()))
                    .build();
        }
    }
}
