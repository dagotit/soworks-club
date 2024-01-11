package com.gmail.dlwk0807.dagotit.util;

import com.gmail.dlwk0807.dagotit.entity.Authority;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.gmail.dlwk0807.dagotit.util.SecurityUtil.getCurrentMemberId;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        Long id = getCurrentMemberId();
        Member member = memberRepository.findById(id).orElseThrow();
        return member;
    }

    public String getCurrentMemberEmail() {
        return getCurrentMember().getEmail();
    }

    public boolean isAdmin() {
        Member member = getCurrentMember();
        return Authority.ROLE_ADMIN.equals(member.getAuthority());
    }
}