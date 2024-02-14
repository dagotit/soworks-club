package com.gmail.dlwk0807.dagachi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.dlwk0807.dagachi.dto.member.MemberUpdateDTO;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.UniqueKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
@Entity
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String name;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    private String nickname;
    private String birth;
    private LocalDateTime lastLoginDate;
    private LocalDateTime emailAuth;
    private String status;
    private String profileImage;
    private String repNameTitle;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_title",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id"))
    private List<Title> titles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Authority authority;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Attendance> attendanceList = new ArrayList<>();


    public void setPassword(String password) {
        this.password = password;
    }

    public void update(MemberUpdateDTO memberUpdateDto, Company company) {
        if (StringUtils.isNotBlank(memberUpdateDto.getName())) {
            this.name = memberUpdateDto.getName();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getNickname())) {
            this.nickname = memberUpdateDto.getNickname();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getBirth())) {
            this.birth = memberUpdateDto.getBirth();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getStatus())) {
            this.status = memberUpdateDto.getStatus();
        }
        if (company != null) {
            this.company = company;
        }
    }

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateAuthority(String authority) {
        this.authority = Authority.valueOf(authority);
    }

    public void updateLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

}
