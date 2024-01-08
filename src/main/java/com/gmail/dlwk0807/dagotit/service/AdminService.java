package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.dto.*;
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
public class AdminService {
    private final MemberRepository memberRepository;

    public String sendAlarm(AdminSendAlarmDto adminSendAlarmDto) {

        return null;

    }
}
