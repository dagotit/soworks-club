package com.gmail.dlwk0807.dagachi.dto.alarm;

import com.gmail.dlwk0807.dagachi.entity.Alarm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String readYn;

    public static AlarmResponseDTO of(Alarm alarm) {
        return AlarmResponseDTO.builder()
                .id(alarm.getId())
                .title(alarm.getTitle())
                .content(alarm.getContent())
                .readYn(alarm.getReadYn())
                .build();
    }
}
