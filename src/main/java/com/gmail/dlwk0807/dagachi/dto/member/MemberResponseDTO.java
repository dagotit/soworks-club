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
public class MemberResponseDTO {
    private String email;
    private String name;
    private String nickname;
    private Long memberId;
    private String profileImage;

    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(member.getEmail(), member.getName(), member.getNickname(), member.getId(), member.getProfileImage());
    }

}
