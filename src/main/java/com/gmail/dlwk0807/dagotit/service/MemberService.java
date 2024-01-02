package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.MemberDeleteDto;
import com.gmail.dlwk0807.dagotit.dto.MemberResponseDto;
import com.gmail.dlwk0807.dagotit.dto.MemberUpdateDto;
import com.gmail.dlwk0807.dagotit.dto.RequestPassword;
import com.gmail.dlwk0807.dagotit.entity.Authority;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gmail.dlwk0807.dagotit.util.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public void updatePassword(RequestPassword requestPassword) {
        Long id = getCurrentMemberId();
        Member member = memberRepository.findById(id).orElseThrow();
        member.setPassword(passwordEncoder.encode(requestPassword.getUpdatePassword()));

    }

    public Long memberUpdate(MemberUpdateDto memberUpdateDto) {
        Long id = getCurrentMemberId();
        Member member = memberRepository.findById(id).orElseThrow();
        member.update(memberUpdateDto);
        return member.getId();
    }

    public void memberDelete(MemberDeleteDto memberDeleteDto, User user) {
        boolean adminYn = user.getAuthorities().stream().anyMatch(v -> Authority.ROLE_ADMIN.equals(Authority.valueOf(v.getAuthority())));
        if (adminYn) {
            Member member = memberRepository.findByEmail(memberDeleteDto.getEmail()).orElseThrow();
            memberRepository.delete(member);
        }
    }
}
