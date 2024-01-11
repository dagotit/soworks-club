package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.admin.AdminSendAlarmDTO;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
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
