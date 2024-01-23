package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.dto.admin.AdminSendAlarmDTO;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final MemberRepository memberRepository;

    public String sendAlarm(AdminSendAlarmDTO adminSendAlarmDto) {

        return null;

    }
}
