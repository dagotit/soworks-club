package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.image.ProfileImageUploadDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberDeleteDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberUpdateDTO;
import com.gmail.dlwk0807.dagotit.dto.member.RequestPasswordDTO;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.util.AuthUtil;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    private final ProfileImageCloudService profileImageCloudService;


    public MemberResponseDTO findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> MemberResponseDTO.of(member))
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDTO findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> MemberResponseDTO.of(member))
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public void updatePassword(RequestPasswordDTO requestPasswordDto) {

        if (!authUtil.isAdmin()) {
            if (!requestPasswordDto.getEmail().equals(authUtil.getCurrentMemberEmail())) {
                throw new AuthenticationNotMatchException();
            }
        }

        Member member = memberRepository.findByEmail(requestPasswordDto.getEmail()).orElseThrow();
        member.setPassword(passwordEncoder.encode(requestPasswordDto.getUpdatePassword()));

    }


    public Long memberUpdate(MemberUpdateDTO memberUpdateDto) {
        if (!authUtil.isAdmin()) {
            if (!memberUpdateDto.getEmail().equals(authUtil.getCurrentMemberEmail())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Member member = memberRepository.findByEmail(memberUpdateDto.getEmail()).orElseThrow();
        member.update(memberUpdateDto);
        return member.getId();
    }

    public void memberDelete(MemberDeleteDTO memberDeleteDto) {
        if (!authUtil.isAdmin()) {
            if (!memberDeleteDto.getEmail().equals(authUtil.getCurrentMemberEmail())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Member member = memberRepository.findByEmail(memberDeleteDto.getEmail()).orElseThrow();
        memberRepository.delete(member);
    }

    public String profileUpload(MultipartFile file, Long memberId, User user) {

        String imageName = null;

        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(memberId)) {
                throw new AuthenticationNotMatchException();
            }
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));

        //모임 이미지 저장
        if (!file.isEmpty()) {
            imageName = profileImageCloudService.uploadProfileImage(file);
            member.updateProfileImage(imageName);
        }

        return imageName;
    }

}
