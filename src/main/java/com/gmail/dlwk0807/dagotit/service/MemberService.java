package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagotit.dto.member.MemberDeleteDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberUpdateDTO;
import com.gmail.dlwk0807.dagotit.dto.member.RequestPasswordDTO;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
                .orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
    }

    public MemberResponseDTO findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> MemberResponseDTO.of(member))
                .orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
    }

    public void updatePassword(RequestPasswordDTO requestPasswordDto) {

        Member member = memberRepository.findById(requestPasswordDto.getMemberId()).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
        member.setPassword(passwordEncoder.encode(requestPasswordDto.getUpdatePassword()));

    }


    public MemberResponseDTO memberUpdate(MemberUpdateDTO memberUpdateDto) {

        Member member = memberRepository.findById(memberUpdateDto.getMemberId()).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
        member.update(memberUpdateDto);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
        return memberResponseDTO;
    }

    public String memberDelete(MemberDeleteDTO memberDeleteDto) {

        Member member = memberRepository.findByEmail(memberDeleteDto.getEmail()).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
        memberRepository.delete(member);

        return "ok";
    }

    public String profileUpload(MultipartFile file, Long memberId, User user) {

        String imageName = null;

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));

        //모임 이미지 저장
        if (!file.isEmpty()) {
            try {
                imageName = profileImageCloudService.uploadProfileImage(file);
                member.updateProfileImage(imageName);
            } catch (Exception e) {
                throw new CustomRespBodyException("이미지 업로드에 실패하였습니다.");
            }
        }

        return imageName;
    }

}
