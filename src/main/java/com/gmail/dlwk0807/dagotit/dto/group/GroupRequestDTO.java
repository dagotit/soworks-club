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
    private Long id;
    private String category;
    private String name;
    private Long memberId;
    private String description;
    @NotBlank(message = "시작일시는 필수입니다.")
    private String strStartDateTime;
    @NotBlank(message = "종료일시는 필수입니다.")
    private String strEndDateTime;
    private String groupImage;
    private Long groupMaxNum;

    public Group toGroup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(strStartDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(strEndDateTime, formatter);

        return Group.builder()
                .category(category)
                .name(name)
                .memberId(memberId)
                .groupImage("https://storage.googleapis.com/dagachi-image-bucket/default/group_default.png")
                .description(description)
                .status(GroupStatus.WAITING)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .groupMaxNum(groupMaxNum)
                .build();
    }

    public void setCurrentMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
