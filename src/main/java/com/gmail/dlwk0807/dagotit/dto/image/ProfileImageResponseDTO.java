package com.gmail.dlwk0807.dagotit.dto.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileImageResponseDTO {

    private String profileImg;

    @Builder
    public ProfileImageResponseDTO(String profileImg) {
        this.profileImg = profileImg;
    }
}
