package com.gmail.dlwk0807.dagotit.dto;

import com.gmail.dlwk0807.dagotit.entity.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class GroupResponseDto {
    private Long Id;
    private String category;
    private String name;
    private String memberId;
    private String picture;
    private String description;
    private String status;
    private String attachId;
    private String strStartDateTime;
    private String strEndDateTime;
    private String allDay;

    public static GroupResponseDto of(Group group) {
        return GroupResponseDto.builder()
                .Id(group.getId())
                .category(group.getCategory())
                .name(group.getName())
                .memberId(group.getMemberId())
                .picture(group.getPicture())
                .description(group.getDescription())
                .status(group.getStatus())
                .attachId(group.getAttachId())
                .strStartDateTime(group.getStartDateTime().toString())
                .strEndDateTime(group.getEndDateTime().toString())
                .allDay(group.getAllDay())
                .build();
    }
}
