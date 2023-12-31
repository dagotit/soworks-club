package com.gmail.dlwk0807.dagotit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dlwk0807.dagotit.dto.MemberUpdateDto;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
@Entity
public class Member extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String address;
    private String addressDtl;
    private String bizNo;
    private String name;
    private String companyName;
    private String companyDate;
    private String nickname;
    private String birth;
    private LocalDateTime lastLoginDate;
    private LocalDateTime emailAuth;
    private String status;
    private String picture;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"member"})
    private List<Attendance> attendanceList = new ArrayList<>();

    @Builder
    public Member(String email, String password, String address, String addressDtl, String bizNo, String name, String companyName, String companyDate, String nickname, String birth, LocalDateTime lastLoginDate, LocalDateTime emailAuth, String status, String picture, Authority authority) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.addressDtl = addressDtl;
        this.bizNo = bizNo;
        this.name = name;
        this.companyName = companyName;
        this.companyDate = companyDate;
        this.nickname = nickname;
        this.birth = birth;
        this.lastLoginDate = lastLoginDate;
        this.emailAuth = emailAuth;
        this.status = status;
        this.picture = picture;
        this.authority = authority;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void update(MemberUpdateDto memberUpdateDto) {
        if (StringUtils.isNotBlank(memberUpdateDto.getAddress())) {
            this.address = memberUpdateDto.getAddress();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getAddressDtl())) {
            this.addressDtl = memberUpdateDto.getAddressDtl();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getBizNo())) {
            this.bizNo = memberUpdateDto.getBizNo();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getName())) {
            this.name = memberUpdateDto.getName();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getCompanyName())) {
            this.companyName = memberUpdateDto.getCompanyName();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getCompanyDate())) {
            this.companyDate = memberUpdateDto.getCompanyDate();
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
        if (StringUtils.isNotBlank(memberUpdateDto.getPicture())) {
            this.picture = memberUpdateDto.getPicture();
        }
        if (StringUtils.isNotBlank(memberUpdateDto.getAuthority())) {
            this.authority = Authority.valueOf(memberUpdateDto.getAuthority());
        }
    }

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }
}
