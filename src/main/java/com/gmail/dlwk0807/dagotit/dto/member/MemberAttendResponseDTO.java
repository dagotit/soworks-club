package com.gmail.dlwk0807.dagotit.dto.member;

import com.gmail.dlwk0807.dagotit.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAttendResponseDTO {
    private String email;
    private String name;
    private String nickname;
    private Long id;

    public static MemberAttendResponseDTO of(Member member) {
        return new MemberAttendResponseDTO(member.getEmail(), member.getName(), member.getNickname(), member.getId());
    }
}
