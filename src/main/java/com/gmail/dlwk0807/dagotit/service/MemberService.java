package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.dto.MemberDeleteDto;
import com.gmail.dlwk0807.dagotit.dto.MemberResponseDto;
import com.gmail.dlwk0807.dagotit.dto.MemberUpdateDto;
import com.gmail.dlwk0807.dagotit.dto.RequestPasswordDto;
import com.gmail.dlwk0807.dagotit.entity.Authority;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gmail.dlwk0807.dagotit.util.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDto findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public void updatePassword(RequestPasswordDto requestPasswordDto) {

        if (!isAdmin()) {
            if (!requestPasswordDto.getEmail().equals(getCurrentMemberEmail())) {
                throw new AuthenticationNotMatchException();
            }
        }

        Member member = memberRepository.findByEmail(requestPasswordDto.getEmail()).orElseThrow();
        member.setPassword(passwordEncoder.encode(requestPasswordDto.getUpdatePassword()));

    }


    public Long memberUpdate(MemberUpdateDto memberUpdateDto) {
        if (!isAdmin()) {
            if (!memberUpdateDto.getEmail().equals(getCurrentMemberEmail())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Member member = memberRepository.findByEmail(memberUpdateDto.getEmail()).orElseThrow();
        member.update(memberUpdateDto);
        return member.getId();
    }

    public void memberDelete(MemberDeleteDto memberDeleteDto) {
        if (!isAdmin()) {
            if (!memberDeleteDto.getEmail().equals(getCurrentMemberEmail())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Member member = memberRepository.findByEmail(memberDeleteDto.getEmail()).orElseThrow();
        memberRepository.delete(member);
    }

    public Member getCurrentMember() {
        Long id = getCurrentMemberId();
        Member member = memberRepository.findById(id).orElseThrow();
        return member;
    }

    private String getCurrentMemberEmail() {
        return getCurrentMember().getEmail();
    }

    public boolean isAdmin() {
        Member member = getCurrentMember();
        return Authority.ROLE_ADMIN.equals(member.getAuthority());
    }
}
