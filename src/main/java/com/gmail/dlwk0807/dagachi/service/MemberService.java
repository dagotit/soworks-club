package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.dto.member.*;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.CompanyRepository;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.dlwk0807.dagachi.util.FileUtils.validateImageFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;
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

        Member member = authUtils.getCurrentMember();
        member.setPassword(passwordEncoder.encode(requestPasswordDto.getUpdatePassword()));
    }


    public MemberResponseDTO memberUpdate(MemberUpdateDTO memberUpdateDto) {

        Member member = authUtils.getCurrentMember();
        member.update(memberUpdateDto, companyRepository.findByBizNo(memberUpdateDto.getBizNo()).orElseThrow(() -> new CustomRespBodyException("[bizNo] 회사 정보가 없습니다.")));

        return MemberResponseDTO.of(member);
    }

    public String memberDelete(MemberDeleteDTO memberDeleteDto) {

        Member member = memberRepository.findById(memberDeleteDto.getMemberId()).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
        memberRepository.delete(member);

        return "ok";
    }

    public String profileUpload(MultipartFile file) {

        String imageName = null;

        Member member = authUtils.getCurrentMember();

        //모임 이미지 저장
        if (!file.isEmpty()) {
            //파일 확장자 체크
            validateImageFile(file);
            try {
                imageName = profileImageCloudService.uploadProfileImage(file);
                String deleteResult = profileImageCloudService.deleteProfileImage(member.getProfileImage());
                log.info("기존 이미지 삭제 결과 : {}", deleteResult);
                member.updateProfileImage(imageName);
            } catch (Exception e) {
                throw new CustomRespBodyException("이미지 업로드에 실패하였습니다.");
            }
        }

        return imageName;
    }

    public ChkAdminResponseDTO checkAdmin(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> ChkAdminResponseDTO.of(member))
                .orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
    }

}
