package com.gmail.dlwk0807.dagachi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.dlwk0807.dagachi.dto.group.GroupUpdateRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GROUP_MST")
@Entity
@Builder
public class Group extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_category",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();
    private String name;
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnore
    private Company company;

    private String groupImage;
    private String description;
    @Enumerated(EnumType.STRING)
    private GroupStatus status;
    private String allDay;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Min(value = 2)
    private Long groupMaxNum;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Builder.Default
    private List<GroupAttend> groupAttendList = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Builder.Default
    private List<GroupFile> groupFileList = new ArrayList<>();


    public void update(GroupUpdateRequestDTO requestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        if (StringUtils.isNotBlank(requestDto.getName())) {
            this.name = requestDto.getName();
        }
        if (StringUtils.isNotBlank(requestDto.getDescription())) {
            this.description = requestDto.getDescription();
        }
        if (StringUtils.isNotBlank(requestDto.getStrStartDateTime())) {
            this.startDateTime = LocalDateTime.parse(requestDto.getStrStartDateTime(), formatter);
        }
        if (StringUtils.isNotBlank(requestDto.getStrEndDateTime())) {
            this.endDateTime = LocalDateTime.parse(requestDto.getStrEndDateTime(), formatter);
        }
        if (requestDto.getGroupMaxNum() != null) {
            this.groupMaxNum = requestDto.getGroupMaxNum();
        }
    }

    public void updateStatus(GroupStatus status) {
        this.status = status;
    }

    public void updateImageName(String groupImage) {
        this.groupImage = groupImage;
    }

    public void updateCategory(List<Category> categories) {
        this.categories = categories;
    }
}
