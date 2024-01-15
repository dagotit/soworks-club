package com.gmail.dlwk0807.dagotit.dto.group;

import com.gmail.dlwk0807.dagotit.entity.Group;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupResponseDTO {
    private Long Id;
    private String category;
    private String name;
    private String memberId;
    private String description;
    private String status;
    private String groupImage;
    private String strStartDateTime;
    private String strEndDateTime;

    public static GroupResponseDTO of(Group group) {
        return GroupResponseDTO.builder()
                .Id(group.getId())
                .category(group.getCategory())
                .name(group.getName())
                .memberId(group.getMemberId())
                .description(group.getDescription())
                .status(String.valueOf(group.getStatus()))
                .strStartDateTime(group.getStartDateTime().toString())
                .strEndDateTime(group.getEndDateTime().toString())
                .groupImage(group.getGroupImage())
                .build();
    }

    public void updateGroupImg(String groupImage) {
        this.groupImage = groupImage;
    }
}
