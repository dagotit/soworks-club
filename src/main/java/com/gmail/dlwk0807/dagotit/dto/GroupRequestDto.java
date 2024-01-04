package com.gmail.dlwk0807.dagotit.dto;

import com.gmail.dlwk0807.dagotit.entity.Group;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class GroupRequestDto {
    private Long Id;
    private String category;
    private String name;
    private String memberId;
    private String picture;
    private String description;
    private String status;
    private String attachId;
    @NotBlank(message = "시작일시는 필수입니다.")
    private String strStartDateTime;
    @NotBlank(message = "종료일시는 필수입니다.")
    private String strEndDateTime;
    private String allDay;

    public Group toGroup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(strStartDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(strEndDateTime, formatter);

        return Group.builder()
                .category(category)
                .name(name)
                .memberId(memberId)
                .picture(picture)
                .description(description)
                .status(status)
                .attachId(attachId)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .allDay(allDay)
                .build();
    }

    public void setCurrentMemberId(String memberId) {
        this.memberId = memberId;
    }
}
