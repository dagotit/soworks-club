package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.entity.Category;
import com.gmail.dlwk0807.dagachi.entity.Group;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class GroupResponseDTO {
    private Long groupId;
    private Category category;
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
    private Integer groupJoinNum;
    private String masterYn;

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
                .groupJoinNum(group.getGroupAttendList().size())
                .masterYn("N")
                .build();
    }

    public void updateMasterYn(String masterYn) {
        this.masterYn = masterYn;
    }

}
