package com.gmail.dlwk0807.dagachi.dto.member;

import com.gmail.dlwk0807.dagachi.entity.Authority;
import com.gmail.dlwk0807.dagachi.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChkAdminResponseDTO {
    private String adminYn;

    public static ChkAdminResponseDTO of(Member member) {
        if (member.getAuthority().equals(Authority.ROLE_ADMIN)) {
            return new ChkAdminResponseDTO("Y");
        }
        return new ChkAdminResponseDTO("N");
    }

}
