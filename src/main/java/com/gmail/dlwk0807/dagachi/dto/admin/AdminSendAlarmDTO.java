package com.gmail.dlwk0807.dagachi.dto.admin;

import com.gmail.dlwk0807.dagachi.core.aspect.AOPMemberIdDTO;
import com.gmail.dlwk0807.dagachi.entity.Alarm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSendAlarmDTO extends AOPMemberIdDTO {

    @Schema(description = "알림내용", example = "내용")
    private String content;
    @Schema(description = "알림제목", example = "제목")
    private String title;
    @Schema(description = "받는사람 ID", example = "2")
    private Long receiveId;

}
