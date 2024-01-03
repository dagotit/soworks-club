package com.gmail.dlwk0807.dagotit.dto;

import com.gmail.dlwk0807.dagotit.entity.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GroupRequestDto {
    private String category;
    private String name;
    private String memberId;
    private String picture;
    private String description;
    private String status;
    private String attachId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Group toGroup() {
        return Group.builder()
                .category(category)
                .name(name)
                .memberId(memberId)
                .picture(picture)
                .description(description)
                .status(status)
                .attachId(attachId)
                .build();
    }
}
