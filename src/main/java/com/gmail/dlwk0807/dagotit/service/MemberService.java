package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.dto.image.ProfileImageResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberDeleteDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberUpdateDTO;
import com.gmail.dlwk0807.dagotit.dto.member.RequestPasswordDTO;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileImageService profileImageService;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    public MemberResponseDTO findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
                    try {
                        ProfileImageResponseDTO image = profileImageService.findImage(member.getId());
                        memberResponseDTO.setProfileImage(image.getProfileImg());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return memberResponseDTO;
                })
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDTO findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> {
                    MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
                    try {
                        ProfileImageResponseDTO image = profileImageService.findImage(member.getId());
                        memberResponseDTO.setProfileImage(image.getProfileImg());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return memberResponseDTO;
                })
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

}
