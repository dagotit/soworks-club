package com.gmail.dlwk0807.dagachi.dto.member;

import com.gmail.dlwk0807.dagachi.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberAttendResponseDTO {
    private String email;
    private String name;
    private String nickname;
    private Long memberId;
    private String profileImage;

    public static MemberAttendResponseDTO of(Member member) {
        return MemberAttendResponseDTO.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .memberId(member.getId())
                .profileImage(member.getProfileImage())
                .build();
    }
}
