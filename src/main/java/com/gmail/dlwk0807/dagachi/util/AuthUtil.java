package com.gmail.dlwk0807.dagachi.util;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.entity.Authority;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtil.getCurrentMemberAuthority;
import static com.gmail.dlwk0807.dagachi.util.SecurityUtil.getCurrentMemberId;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        Long id = getCurrentMemberId();
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
        return member;
    }

    public String getCurrentMemberEmail() {
        return getCurrentMember().getEmail();
    }

    public boolean isAdmin() {
        String currentAuthority = getCurrentMemberAuthority();
        return Authority.ROLE_ADMIN.equals(Authority.valueOf(currentAuthority));
    }
}