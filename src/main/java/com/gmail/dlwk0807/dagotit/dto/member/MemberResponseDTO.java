package com.gmail.dlwk0807.dagotit.dto.member;

import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.entity.ProfileImage;
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
    private Long id;
    private String profileImage;

    public MemberResponseDTO(String email, String name, String nickname, Long id) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.id = id;
    }

    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(member.getEmail(), member.getName(), member.getNickname(), member.getId());
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
