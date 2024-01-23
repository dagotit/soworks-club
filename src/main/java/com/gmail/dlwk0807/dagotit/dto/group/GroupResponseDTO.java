package com.gmail.dlwk0807.dagotit.dto.group;

import com.gmail.dlwk0807.dagotit.entity.Group;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class GroupResponseDTO {
    private Long groupId;
    private String category;
    private String name;
    private Long memberId;
    private String description;
    private String status;
    private String groupImage;
    private String strStartDate;
    private String strStartTime;
    private String strEndDate;
    private String strEndTime;
    private Long groupMaxNum;
    private Integer numberPersons;

    public static GroupResponseDTO of(Group group) {
        return GroupResponseDTO.builder()
                .groupId(group.getId())
                .category(group.getCategory())
                .name(group.getName())
                .memberId(group.getMemberId())
                .description(group.getDescription())
                .status(String.valueOf(group.getStatus()))
                .strStartDate(group.getStartDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .strStartTime(group.getStartDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HHmm")))
                .strEndDate(group.getEndDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .strEndTime(group.getEndDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HHmm")))
                .groupImage(group.getGroupImage())
                .groupMaxNum(group.getGroupMaxNum())
                .numberPersons(group.getGroupAttendList().size())
                .build();
    }

    public void updateGroupImg(String groupImage) {
        this.groupImage = groupImage;
    }
}
