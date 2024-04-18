package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.dto.alarm.AlarmResponseDTO;
import com.gmail.dlwk0807.dagachi.entity.Alarm;
import com.gmail.dlwk0807.dagachi.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public List<AlarmResponseDTO> listAlarm(Long receiveId) {
        return alarmRepository.findAllByReceiveId(receiveId).stream()
                .map(AlarmResponseDTO::of).collect(Collectors.toList());

    }

    public AlarmResponseDTO findAlarm(Long id) {
        Alarm alarm = alarmRepository.findById(id).orElseThrow(() -> new CustomRespBodyException("알림이 존재하지 않습니다."));
        if (!getCurrentMemberId().equals(alarm.getReceiveId())) {
            throw new CustomRespBodyException("알림 id를 확인해주세요.");
        }
        if (alarm.getReadYn().equals("N")) {
            alarm.updateReadYn();
        }

        return AlarmResponseDTO.of(alarm);
    }
}
