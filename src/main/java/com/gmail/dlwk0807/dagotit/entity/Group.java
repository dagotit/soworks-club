package com.gmail.dlwk0807.dagotit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dlwk0807.dagotit.dto.GroupRequestDto;
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
    private String memberId;
    private String picture;
    private String description;
    private String status;
    private String attachId;
    private String allDay;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties({"group"})
    private List<GroupAttend> groupAttendList = new ArrayList<>();

    @Builder
    public Group(String category, String name, String memberId, String picture, String description, String status, String attachId, String allDay, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.category = category;
        this.name = name;
        this.memberId = memberId;
        this.picture = picture;
        this.description = description;
        this.status = status;
        this.attachId = attachId;
        this.allDay = allDay;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void update(GroupRequestDto requestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(requestDto.getStrStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(requestDto.getStrEndDateTime(), formatter);

        this.category = requestDto.getCategory();
        this.name = requestDto.getName();
        this.memberId = requestDto.getMemberId();
        this.picture = requestDto.getPicture();
        this.description = requestDto.getDescription();
        this.status = requestDto.getStatus();
        this.attachId = requestDto.getAttachId();
        this.allDay = requestDto.getAllDay();
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
