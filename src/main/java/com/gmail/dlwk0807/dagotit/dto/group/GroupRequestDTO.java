package com.gmail.dlwk0807.dagotit.dto.group;

import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.entity.GroupStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class GroupRequestDTO {
    private Long Id;
    private String category;
    private String name;
    private String memberId;
    private String groupImg;
    private String groupImgName;
    private String description;
    @NotBlank(message = "시작일시는 필수입니다.")
    private String strStartDateTime;
    @NotBlank(message = "종료일시는 필수입니다.")
    private String strEndDateTime;

    public Group toGroup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(strStartDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(strEndDateTime, formatter);

        return Group.builder()
                .category(category)
                .name(name)
                .memberId(memberId)
                .description(description)
                .status(GroupStatus.WAITING)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .groupImgName(groupImgName)
                .build();
    }

    public void setCurrentMemberId(String memberId) {
        this.memberId = memberId;
    }
}
