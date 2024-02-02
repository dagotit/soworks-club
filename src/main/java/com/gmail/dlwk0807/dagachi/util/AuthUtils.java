package com.gmail.dlwk0807.dagachi.util;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.entity.Authority;
import com.gmail.dlwk0807.dagachi.entity.Company;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.CompanyRepository;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberAuthority;
import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberId;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

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

    public Company getCurrentCompany() {
        Member member = getCurrentMember();
        Company company = companyRepository.findById(member.getCompany().getId()).orElseThrow(() -> new CustomRespBodyException("회사정보가 존재하지 않습니다."));
        return company;
    }

    public void checkDiffCompany(Group group) {
        if (!group.getCompany().getId().equals(getCurrentCompany().getId())) {
            throw new CustomRespBodyException("모임정보를 확인해주세요");
        }
    }
}