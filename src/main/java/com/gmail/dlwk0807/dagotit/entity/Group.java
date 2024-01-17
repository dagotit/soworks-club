package com.gmail.dlwk0807.dagotit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.group.GroupResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GROUP_MST")
@Entity
public class Group extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String name;
    private Long memberId;
    private String groupImage;
    private String description;
    @Enumerated(EnumType.STRING)
    private GroupStatus status;
    private String allDay;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long groupMaxNum;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<GroupAttend> groupAttendList = new ArrayList<>();

    @Builder
    public Group(String category, String name, Long memberId, String groupImage, String description, GroupStatus status, String allDay, LocalDateTime startDateTime, LocalDateTime endDateTime, Long groupMaxNum) {
        this.category = category;
        this.name = name;
        this.memberId = memberId;
        this.groupImage = groupImage;
        this.description = description;
        this.status = status;
        this.allDay = allDay;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.groupMaxNum = groupMaxNum;
    }

    public void update(GroupRequestDTO requestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(requestDto.getStrStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(requestDto.getStrEndDateTime(), formatter);

        this.category = requestDto.getCategory();
        this.name = requestDto.getName();
        this.description = requestDto.getDescription();
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void updateStatus(GroupStatus status) {
        this.status = status;
    }

    public void updateImageName(String groupImage) {
        this.groupImage = groupImage;
    }
}
