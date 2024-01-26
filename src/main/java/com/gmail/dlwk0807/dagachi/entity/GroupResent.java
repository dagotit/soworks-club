package com.gmail.dlwk0807.dagachi.entity;

import com.gmail.dlwk0807.dagachi.dto.group.GroupResponseDTO;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RedisHash("userIdx")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupResent {
    private Long groupId;
    private List<Category> categories;
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

    public static GroupResent of(Group group) {
        return GroupResent.builder()
                .groupId(group.getId())
                .categories(group.getCategories())
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
                .build();
    }

    public void updateMasterYn(String masterYn) {
        this.masterYn = masterYn;
    }
}
