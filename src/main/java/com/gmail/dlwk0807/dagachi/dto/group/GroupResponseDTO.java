package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.entity.Category;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.GroupFile;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberId;

@Getter
@Builder
public class GroupResponseDTO {
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
    private String joinYn;
    private List<GroupFile> groupFiles;

    public static GroupResponseDTO of(Group group) {


        return GroupResponseDTO.builder()
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
                .masterYn(updateMasterYn(group))
                .joinYn(updateJoinYn(group, getCurrentMemberId()))
                .groupFiles(group.getGroupFileList())
                .build();
    }

    public static String updateMasterYn(Group group) {
        return getCurrentMemberId().equals(group.getMemberId()) ? "Y" : "N";
    }

    public static String updateJoinYn(Group group, Long currentMemberId) {
        return group.getGroupAttendList().stream()
                .anyMatch(ga -> ga.getMember().getId().equals(currentMemberId)) ? "Y" : "N";
    }

}
