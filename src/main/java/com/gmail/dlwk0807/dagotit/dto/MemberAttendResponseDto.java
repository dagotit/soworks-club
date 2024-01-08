package com.gmail.dlwk0807.dagotit.dto;

import com.gmail.dlwk0807.dagotit.entity.GroupAttend;
import com.gmail.dlwk0807.dagotit.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAttendResponseDto {
    private String email;
    private String name;
    private String nickname;
    private Long id;

    public static MemberAttendResponseDto of(Member member) {
        return new MemberAttendResponseDto(member.getEmail(), member.getName(), member.getNickname(), member.getId());
    }
}
